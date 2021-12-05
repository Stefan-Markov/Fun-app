package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.errorHandling.MyResourceNotFoundException;
import com.fullstackproject.errorHandling.Success;
import com.fullstackproject.models.Role;
import com.fullstackproject.models.User;
import com.fullstackproject.models.dto.UserDto;
import com.fullstackproject.repositories.RoleRepository;
import com.fullstackproject.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

import static com.fullstackproject.constants.Constants.API_HOST;
import static com.fullstackproject.constants.Constants.ROLE_USER;

@RestController
@CrossOrigin(API_HOST)
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public UserController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder,
                          RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    @ResponseBody
    public Object register(@RequestBody @Valid User userData, BindingResult bindingResult) {

        Optional<User> tryUser = this.userRepository.findByUsername(userData.getUsername());
        if (tryUser.isPresent()) {
            ErrorRest errorRest = new ErrorRest();

            errorRest.setMessage("Username is already taken!");
            errorRest.setCause("Username is already taken!");
            errorRest.setCode(401);
            return errorRest;
        }


        if (bindingResult.hasErrors()) {
            ErrorRest errorRest = new ErrorRest();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            errorRest.setCode(401);
            for (FieldError e : errors) {
                message.add(e.getDefaultMessage());
            }
            errorRest.setMessage("Update Failed");
            errorRest.setCause(message.toString());
            return errorRest;
        }

        try {
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

            Success success = new Success();
            success.setCode(200);
            success.setMessage("Successful!");
            return success;
        } catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Username is already taken", exc);
        }
    }

    @GetMapping("/account/:{username}")
    @ResponseBody
    @PreAuthorize("#username == authentication.name && hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public UserDto getUserByUsername(@PathVariable String username) {
        Optional<User> byUsername = this.userRepository.findByUsername(username);

        return this.modelMapper.map(byUsername.get(), UserDto.class);
    }


    @GetMapping("/:{username}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> getUsersByUsername(@PathVariable String username) {

        return this.userRepository.findAllBySimilarUsername(username);
    }

    @PutMapping("/role/:{username}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public Object removeRoleOnUser(@PathVariable String username) {
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
        user.get().getAuthorities().remove(role);
        this.userRepository.save(user.get());

        success.setMessage("User updated successful!");
        success.setCode(200);
        return success;
    }


    @PutMapping("/role-admin/:{username}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public Object upgradeRoleOnUser(@PathVariable String username) {
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

        if (tryRemoveRole.contains(role)) {
            success.setMessage("User already have admin role!");
            success.setCode(401);
            return success;
        }
        user.get().getAuthorities().add(role);
        this.userRepository.save(user.get());

        success.setMessage("User updated successful!");
        success.setCode(200);
        return success;
    }


    @DeleteMapping("/user/:{username}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public Object deleteUser(@PathVariable String username) {
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
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            success.setCode(401);
            success.setMessage("Given username don't exist!");
            return success;
        }

        this.userRepository.delete(user.get());

        success.setCode(200);
        success.setMessage("User with " + username + " deleted!");
        return success;
    }
}
