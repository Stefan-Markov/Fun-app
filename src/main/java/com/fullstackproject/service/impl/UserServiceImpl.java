package com.fullstackproject.service.impl;

import com.fullstackproject.errorHandling.Success;
import com.fullstackproject.models.dto.UserDto;
import com.fullstackproject.models.entities.Role;
import com.fullstackproject.models.entities.User;
import com.fullstackproject.repositories.RoleRepository;
import com.fullstackproject.repositories.UserRepository;
import com.fullstackproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.fullstackproject.constants.Constants.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    private static Success getSuccessUpgradeRole(String username, RoleRepository roleRepository, UserRepository userRepository) {
        Success success = new Success();
        String principalUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (principalUser.equals(username)) {
            success.setCode(401);
            success.setMessage("You can't modify yourself!");
            return success;
        }

        Role role = roleRepository.findByAuthority("ROLE_ADMIN");
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            success.setCode(401);
            success.setMessage("Given username don't exist!");
            return success;
        }

        Set<Role> tryRemoveRole = new HashSet<>(user.get().getAuthorities());

        if (tryRemoveRole.contains(role)) {
            success.setMessage("User already have admin role!");
            success.setCode(401);
            return success;
        }
        user.get().getAuthorities().add(role);
        userRepository.save(user.get());

        success.setMessage("User updated successful!");
        success.setCode(200);
        return success;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void registerUser(User userData) {
        Role role = this.roleRepository.findByAuthority(ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setUsername(userData.getUsername())
                .setPassword(this.bCryptPasswordEncoder.encode(userData.getPassword()))
                .setAuthorities(roles)
                .setCreatedDate(LocalDateTime.now())
                .setEmail(userData.getEmail());
        this.userRepository.save(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> byUsername = this.userRepository.findByUsername(username);
        return this.modelMapper.map(byUsername.get(), UserDto.class);
    }

    @Override
    public List<String> findAllBySimilarUsername(String username) {
        return this.userRepository.findAllBySimilarUsername(username);
    }

    @Override
    public Success removeRoleOnUser(String username) {
        Success success = new Success();
        String principalUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (principalUser.equals(username)) {
            success.setCode(401);
            success.setMessage("You can't modify yourself!");
            return success;
        }

        Role role = this.roleRepository.findByAuthority("ROLE_ADMIN");
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            success.setCode(401);
            success.setMessage("Given username don't exist!");
            return success;
        }

        Set<Role> tryRemoveRole = new HashSet<>(user.get().getAuthorities());

        if (!tryRemoveRole.contains(role)) {
            success.setMessage("User don't have admin role!");
            success.setCode(401);
            return success;
        }
        if (user.get().getUsername().equals("leonkov")) {
            success.setCode(401);
            success.setMessage("Forbidden action!");
            return success;
        }

        user.get().getAuthorities().remove(role);
        this.userRepository.save(user.get());

        success.setMessage("User updated successful!");
        success.setCode(200);
        return success;
    }

    @Override
    public Success upgradeRoleOnUser(String username) {
        return getSuccessUpgradeRole(username, this.roleRepository, this.userRepository);
    }

    @Override
    public Success deleteUser(String username) {
        return getSuccessDeleteUser(username, this.userRepository);
    }

    public static Success getSuccessDeleteUser(String username, UserRepository userRepository) {
        Success success = new Success();
        String principalUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (principalUser.equals(username)) {
            success.setCode(401);
            success.setMessage("You can't delete yourself!");
            return success;
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            success.setCode(401);
            success.setMessage("Given username don't exist!");
            return success;
        }
        if (user.get().getUsername().equals("leonkov")) {
            success.setCode(401);
            success.setMessage("Forbidden action!");
            return success;
        }

        userRepository.delete(user.get());

        success.setCode(200);
        success.setMessage("User with " + username + " deleted!");
        return success;
    }
}
