package com.fullstackproject.service;

import com.fullstackproject.models.dto.CommentDto;
import com.fullstackproject.models.entities.Comment;
import com.fullstackproject.models.entities.Joke;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    List<Joke> getCommentedJokesByUsername(String username);

    Comment addCommentToJoke(String id, CommentDto commentDto);

    ResponseEntity<?> deleteCommentById(String id);
}
