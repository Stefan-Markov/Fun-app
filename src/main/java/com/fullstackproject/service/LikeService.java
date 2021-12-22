package com.fullstackproject.service;

import org.springframework.http.ResponseEntity;

public interface LikeService {

    ResponseEntity<?> addLikeToJokeByIdAndUsername(String jokeId,String username);
}
