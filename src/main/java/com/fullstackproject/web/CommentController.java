package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.models.Comment;
import com.fullstackproject.models.Joke;
import com.fullstackproject.models.dto.CommentDto;
import com.fullstackproject.repositories.CommentRepository;
import com.fullstackproject.repositories.JokeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fullstackproject.constants.Constants.API_HOST;

@RestController
@Transactional
@CrossOrigin(API_HOST)
public class CommentController {

    private final CommentRepository commentRepository;
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;

    public CommentController(CommentRepository commentRepository,
                             JokeRepository jokeRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.jokeRepository = jokeRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add/comment/:{jokeId}")
    @ResponseBody
    public Object addCommentToJoke(@PathVariable String jokeId,
                                   @RequestBody @Valid CommentDto commentDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            ErrorRest errorRest = new ErrorRest();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            for (FieldError e : errors) {
                message.add(e.getDefaultMessage());
            }
            errorRest.setCode(400);
            errorRest.setMessage("Adding comment Failed");
            errorRest.setCause(message.toString());
            return errorRest;
        }

        Joke joke = this.jokeRepository.findById(jokeId).get();
        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment
                .setJoke(joke)
                .setLocalDate(LocalDateTime.now());

        commentRepository.save(comment);
        return ResponseEntity.status(200).body(comment);

    }

    @DeleteMapping("/add/comment/:{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable String id) {

        Comment comment = this.commentRepository.findById(id).get();

        this.commentRepository.deleteById(id);

        return ResponseEntity.status(200).body(comment);
    }
}
