package com.fullstackproject.service;

import com.fullstackproject.models.dto.CommentDto;
import com.fullstackproject.models.entities.Comment;
import com.fullstackproject.models.entities.Joke;

import java.util.List;

public interface CommentService {

    List<Joke> getCommentedJokesByUsername(String username);

    Comment addCommentToJoke(String id, CommentDto commentDto);
}
