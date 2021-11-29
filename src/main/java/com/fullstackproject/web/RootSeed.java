package com.fullstackproject.web;

import com.fullstackproject.models.User;
import com.fullstackproject.repositories.UserRepository;
import com.fullstackproject.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class RootSeed implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public RootSeed(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {

        if (this.userRepository.count() == 0) {

            this.roleService.seedRolesInDb();

            User user = new User();
            user
                    .setAuthorities(roleService.findAllRoles())
                    .setUsername("leonkov")
                    .setCreatedDate(LocalDateTime.now())
                    .setEmail("leo@abv.bg")
                    .setPassword(this.passwordEncoder.encode("33333"));

            this.userRepository.save(user);
        }
    }
}
