package com.fullstackproject.web;

import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.models.entities.User;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.repositories.UserRepository;
import com.fullstackproject.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RootSeed implements CommandLineRunner {
    private final UserRepository userRepository;
    private final JokeRepository jokeRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final String USERNAME = "leonkov";
    private final String EMAIL = "leo@abv.bg";
    private final String PASSWORD = "33333";

    public RootSeed(UserRepository userRepository, JokeRepository jokeRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jokeRepository = jokeRepository;
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
                    .setUsername(USERNAME)
                    .setCreatedDate(LocalDateTime.now())
                    .setEmail(EMAIL)
                    .setPassword(this.passwordEncoder.encode(PASSWORD));
            this.userRepository.save(user);

            Joke joke = new Joke();
            joke
                    .setCreatedDate(LocalDateTime.now())
                    .setContent("very nice content")
                    .setCreator("leonkov")
                    .setKeyword("keyword very cool")
                    .setTitle("title very cool")
                    .setUser(user);

            Joke jokeS = new Joke();
            jokeS
                    .setCreatedDate(LocalDateTime.now())
                    .setContent("very nice content again")
                    .setCreator("leonkov")
                    .setKeyword("keyword very cool again")
                    .setTitle("title very cool again")
                    .setUser(user);

            Joke jokeT = new Joke();
            jokeT
                    .setCreatedDate(LocalDateTime.now())
                    .setContent("very nice content wow")
                    .setCreator("leonkov")
                    .setKeyword("keyword very cool wow")
                    .setTitle("title very cool wow")
                    .setUser(user);
            List<Joke> jokes = List.of(joke,jokeT,jokeS);
            this.jokeRepository.saveAll(jokes);
        }
    }
}
