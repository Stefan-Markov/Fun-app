package com.fullstackproject.service.impl;

import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.models.entities.Likes;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.repositories.LikeRepository;
import com.fullstackproject.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {
    private final JokeRepository jokeRepository;
    private final LikeRepository likeRepository;

    public LikeServiceImpl(JokeRepository jokeRepository, LikeRepository likeRepository) {
        this.jokeRepository = jokeRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public ResponseEntity<?> addLikeToJokeByIdAndUsername(String jokeId, String username) {
        Optional<Joke> joke = this.jokeRepository.findById(jokeId);
        if (joke.isEmpty()) {
            return ResponseEntity.status(403).build();
        }
        Likes like = new Likes();
        like.setOwnerOfComment(username);
        like.setJoke(joke.get());

        this.likeRepository.save(like);
        return ResponseEntity.status(200).build();
    }
}
