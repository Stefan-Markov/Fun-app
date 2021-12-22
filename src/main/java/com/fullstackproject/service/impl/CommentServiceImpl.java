package com.fullstackproject.service.impl;

import com.fullstackproject.models.dto.CommentDto;
import com.fullstackproject.models.entities.Comment;
import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.repositories.CommentRepository;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, JokeRepository jokeRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.jokeRepository = jokeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Joke> getCommentedJokesByUsername(String username) {
        List<Joke> jokes = new ArrayList<>();
        List<String> jokesIds = this.commentRepository.findJokeIdsByCommentsByUsername(username);

        for (String id : jokesIds) {
            Joke byId = this.jokeRepository.findById(id).get();
            jokes.add(byId);
        }
        return jokes;
    }

    @Override
    public Comment addCommentToJoke(String jokeId, CommentDto commentDto) {

        Joke joke = this.jokeRepository.findById(jokeId).get();
        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setJoke(joke)
                .setLocalDate(LocalDateTime.now());

        commentRepository.save(comment);
        return comment;
    }
}
