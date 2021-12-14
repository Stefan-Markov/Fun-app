package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.models.entities.Comment;
import com.fullstackproject.models.entities.Joke;
import com.fullstackproject.models.entities.Likes;
import com.fullstackproject.models.dto.CommentDto;
import com.fullstackproject.repositories.CommentRepository;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.repositories.LikeRepository;
import com.fullstackproject.repositories.UserRepository;
import com.fullstackproject.security.rolesAuth.IsProfileUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class CommentAndLikesController {

    private final CommentRepository commentRepository;
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public CommentAndLikesController(CommentRepository commentRepository,
                                     JokeRepository jokeRepository, ModelMapper modelMapper, UserRepository userRepository, LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.jokeRepository = jokeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @PostMapping("/add/comment/:{jokeId}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public Object addCommentToJoke(@PathVariable String jokeId,
                                   @RequestBody @Valid CommentDto commentDto, BindingResult bindingResult) {
        ErrorRest errorRest = new ErrorRest();
        ErrorRest checkForAuth = getError(commentDto, errorRest);
        if (checkForAuth != null) return checkForAuth;

        if (bindingResult.hasErrors()) {

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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteCommentById(@PathVariable String id) {
        Comment comment = this.commentRepository.findById(id).get();

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!principal.equals(comment.getOwnerOfComment())) {
            return ResponseEntity.status(401).build();
        }

        this.commentRepository.deleteById(id);

        return ResponseEntity.status(200).body(comment);
    }

    @PostMapping("/add/like/:{id}/:{username}")
    @PreAuthorize("isAuthenticated()")
    @IsProfileUser
    public ResponseEntity<?> addLike(@PathVariable String id, @PathVariable String username) {

        Joke joke = this.jokeRepository.findById(id).get();
        Likes like = new Likes();
        like.setOwnerOfComment(username);
        like.setJoke(joke);

        this.likeRepository.save(like);
        return ResponseEntity.status(200).build();
    }

    private ErrorRest getError(CommentDto commentDto, ErrorRest errorRest) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!principal.equals(commentDto.getOwnerOfComment())) {
            errorRest.setCode(400);
            errorRest.setMessage("Adding comment Failed");
            errorRest.setCause("Failed adding!");
            return errorRest;
        }
        return null;
    }

}
