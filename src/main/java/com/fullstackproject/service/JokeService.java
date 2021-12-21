package com.fullstackproject.service;

import com.fullstackproject.models.dto.JokeDTO;
import com.fullstackproject.models.dto.JokeEditDTO;
import com.fullstackproject.models.entities.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeService {

    Joke createJoke(JokeDTO jokeDTO);

    List<Joke> findAllJokesByUsername(String username);

    Joke getJokeById(String id);

    Optional<Joke> getJokeByIdOptional(String id);

    Joke editJoke(Joke joke, JokeEditDTO jokeDTO);

    void deleteJokeById(String id);

    List<Joke> getLastThreeJokes();

    List<Joke> getJokesByKeyword(String keyword);

    List<Joke> getJokeByMostLikes();

}
