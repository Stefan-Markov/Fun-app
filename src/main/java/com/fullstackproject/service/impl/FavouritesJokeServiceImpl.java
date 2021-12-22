package com.fullstackproject.service.impl;

import com.fullstackproject.models.entities.Favourites;
import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.models.entities.User;
import com.fullstackproject.repositories.FavouritesJokeRepository;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.repositories.UserRepository;
import com.fullstackproject.service.FavouritesJokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouritesJokeServiceImpl implements FavouritesJokeService {

    private final FavouritesJokeRepository favouritesJokeRepository;
    private final UserRepository userRepository;
    private final JokeRepository jokeRepository;

    public FavouritesJokeServiceImpl(FavouritesJokeRepository favouritesJokeRepository, UserRepository userRepository, JokeRepository jokeRepository) {
        this.favouritesJokeRepository = favouritesJokeRepository;
        this.userRepository = userRepository;
        this.jokeRepository = jokeRepository;
    }

    @Override
    public ResponseEntity<?> addFavouriteJoke(String id, String username) {

        Favourites already = this.favouritesJokeRepository.findByUsernameAndId(username, id);

        ResponseEntity<?> build = checkForAuthorAndJokeExists(id, username);
        if (build != null) return build;

        if (already != null) {
            return ResponseEntity.status(403).build();
        }

        Favourites favourites = new Favourites();
        favourites.setUsername(username);
        favourites.setJokeId(id);

        this.favouritesJokeRepository.save(favourites);
        return ResponseEntity.status(200).build();
    }

    private ResponseEntity<?> checkForAuthorAndJokeExists(String id, String username) {
        Optional<Joke> jokeFindById = this.jokeRepository.findById(id);
        Optional<User> byUsername = this.userRepository.findByUsername(username);
        if (jokeFindById.isEmpty() || byUsername.isEmpty()) {
            return ResponseEntity.status(403).build();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getFavouritesJokesByUsername(String username) {
        List<String> jokeIds = this.favouritesJokeRepository.findAllByUsername(username);
        List<Joke> jokes = new ArrayList<>();

        if (jokeIds.size() == 0) {
            return ResponseEntity.status(200).build();
        }

        for (String id : jokeIds) {
            Joke joke = this.jokeRepository.findById(id).get();
            jokes.add(joke);
        }
        return ResponseEntity.status(200).body(jokes);
    }

    @Override
    public ResponseEntity<?> deleteFavouritesJokeByUsernameAndJokeId(String username, String id) {

        ResponseEntity<?> build = checkForAuthorAndJokeExists(id, username);
        if (build != null) return build;

        Favourites already = this.favouritesJokeRepository.findByUsernameAndId(username, id);

        if (already != null) {
            this.favouritesJokeRepository.delete(already);
            return ResponseEntity.status(200).body(already);
        }

        return ResponseEntity.status(200).body(0);
    }

}
