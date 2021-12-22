package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.models.entities.Comment;
import com.fullstackproject.models.dto.CommentDto;
import com.fullstackproject.security.rolesAuth.IsProfileUser;
import com.fullstackproject.service.CommentService;
import com.fullstackproject.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.fullstackproject.constants.Constants.API_HOST;

@RestController
@Transactional
@CrossOrigin(API_HOST)
public class CommentAndLikesController {

    private final LikeService likeService;
    private final CommentService commentService;

    public CommentAndLikesController(LikeService likeService, CommentService commentService) {
        this.likeService = likeService;
        this.commentService = commentService;
    }

    @PostMapping("/add/comment/:{jokeId}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public Object addCommentToJoke(@PathVariable String jokeId,
                                   @RequestBody @Valid CommentDto commentDto, BindingResult bindingResult) {
        ErrorRest errors = new ErrorRest();
        ErrorRest checkForAuth = checkForAuthor(commentDto, errors);
        if (checkForAuth != null) return checkForAuth;

        ErrorRest errorRest = getErrors(bindingResult, errors);
        if (errorRest != null) return errorRest;

        Comment comment = this.commentService.addCommentToJoke(jokeId, commentDto);
        return ResponseEntity.status(200).body(comment);

    }

    private ErrorRest getErrors(BindingResult bindingResult, ErrorRest errorRest) {
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
        return null;
    }

    @DeleteMapping("/add/comment/:{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteCommentById(@PathVariable String id) {
        return this.commentService.deleteCommentById(id);
    }

    @PostMapping("/add/like/:{id}/:{username}")
    @PreAuthorize("isAuthenticated()")
    @IsProfileUser
    public ResponseEntity<?> addLike(@PathVariable String id, @PathVariable String username) {
        return this.likeService.addLikeToJokeByIdAndUsername(id, username);
    }

    private ErrorRest checkForAuthor(CommentDto commentDto, ErrorRest errorRest) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!principal.equals(commentDto.getOwnerOfComment())) {
            errorRest.setCode(402);
            errorRest.setMessage("Adding comment Failed");
            errorRest.setCause("Failed adding!");
            return errorRest;
        }
        return null;
    }
}
