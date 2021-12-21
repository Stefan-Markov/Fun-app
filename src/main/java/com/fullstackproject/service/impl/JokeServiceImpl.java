package com.fullstackproject.service.impl;

import com.fullstackproject.models.dto.JokeDTO;
import com.fullstackproject.models.dto.JokeEditDTO;
import com.fullstackproject.models.entities.Comment;
import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.models.entities.User;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.service.JokeService;
import com.fullstackproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JokeServiceImpl implements JokeService {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JokeRepository jokeRepository;

    public JokeServiceImpl(UserService userService, ModelMapper modelMapper, JokeRepository jokeRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jokeRepository = jokeRepository;
    }

    @Override
    public Joke createJoke(JokeDTO jokeData) {
        Optional<User> user = this.userService.findByUsername(jokeData.getUsername());
        Joke joke = this.modelMapper.map(jokeData, Joke.class);

        joke.setUser(user.get());
        joke.setCreator(user.get().getUsername());
        joke.setCreatedDate(LocalDateTime.now());

        this.jokeRepository.save(joke);

        return joke;
    }

    @Override
    public List<Joke> findAllJokesByUsername(String username) {
        return this.jokeRepository.findAllByUsername(username);
    }

    @Override
    public Joke getJokeById(String id) {
        Optional<Joke> joke = this.jokeRepository.findById(id);
        if (joke.isPresent()) {
            List<Comment> collect = joke.get().getComments()
                    .stream().sorted(Comparator.comparing(Comment::getLocalDate))
                    .collect(Collectors.toList());
            joke.get().setComments(collect);
            return joke.get();
        }
        return null;
    }

    @Override
    public Optional<Joke> getJokeByIdOptional(String id) {
        return this.jokeRepository.findById(id);
    }

    @Override
    public Joke editJoke(Joke joke, JokeEditDTO jokeDTO) {
        joke.setContent(jokeDTO.getContent());
        joke.setKeyword(jokeDTO.getKeyword());
        joke.setTitle(jokeDTO.getTitle());

        this.jokeRepository.save(joke);
        return joke;
    }

    @Override
    public void deleteJokeById(String id) {
        this.jokeRepository.deleteById(id);
    }

    @Override
    public List<Joke> getLastThreeJokes() {
        return this.jokeRepository.findLastThree()
                .stream()
                .sorted(Comparator.comparing(Joke::getCreatedDate).reversed())
                .limit(3).collect(Collectors.toList());
    }

    @Override
    public List<Joke> getJokesByKeyword(String keyword) {
        return this.jokeRepository.findAllByKeyword(keyword)
                .stream()
                .sorted(Comparator.comparing(Joke::getCreatedDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Joke> getJokeByMostLikes() {
        return this.jokeRepository.findJokeWithMostLikes()
                .stream().limit(1).collect(Collectors.toList());
    }


}
