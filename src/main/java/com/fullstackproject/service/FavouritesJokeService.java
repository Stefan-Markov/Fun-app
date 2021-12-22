package com.fullstackproject.service;

import org.springframework.http.ResponseEntity;

public interface FavouritesJokeService {

    ResponseEntity<?> addFavouriteJoke(String id, String username);

    ResponseEntity<?> getFavouritesJokesByUsername(String username);

    ResponseEntity<?> deleteFavouritesJokeByUsernameAndJokeId(String username, String id);
}
