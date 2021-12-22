package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.models.entities.*;
import com.fullstackproject.models.dto.JokeDTO;
import com.fullstackproject.models.dto.JokeEditDTO;
import com.fullstackproject.security.rolesAuth.IsProfileUser;
import com.fullstackproject.service.CommentService;
import com.fullstackproject.service.FavouritesJokeService;
import com.fullstackproject.service.JokeService;
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
import java.util.Optional;

import static com.fullstackproject.constants.Constants.API_HOST;

@RestController
@Transactional
@CrossOrigin(API_HOST)
public class JokeController {

    private final JokeService jokeService;
    private final FavouritesJokeService favouritesJokeService;
    private final CommentService commentService;

    public JokeController(JokeService jokeService,
                          FavouritesJokeService favouritesJokeService, CommentService commentService) {
        this.jokeService = jokeService;
        this.favouritesJokeService = favouritesJokeService;
        this.commentService = commentService;
    }


    @PostMapping("/joke")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public Object createJoke(@RequestBody @Valid JokeDTO jokeData, BindingResult bindingResult) {

        ErrorRest errorRest = getErrors(bindingResult);
        if (errorRest != null) return errorRest;

        Joke joke = this.jokeService.createJoke(jokeData);

        return ResponseEntity.status(200).body(joke);
    }

    private ErrorRest getErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorRest errorRest = new ErrorRest();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            errorRest.setCode(401);
            for (FieldError e : errors) {
                message.add(e.getDefaultMessage());
            }

            errorRest.setMessage("Create of joke Failed");
            errorRest.setCause(message.toString());
            return errorRest;
        }
        return null;
    }

    @GetMapping("/joke-manage/:{username}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    @IsProfileUser
    public ResponseEntity<List<Joke>> getJokes(@PathVariable String username) {
        List<Joke> jokes = this.jokeService.findAllJokesByUsername(username);
        return ResponseEntity.status(200).body(jokes);
    }


    @GetMapping("/joke/:{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Joke> getJokeById(@PathVariable String id) {
        Joke jokeById = this.jokeService.getJokeById(id);
        return ResponseEntity.status(200).body(jokeById);
    }

    @PutMapping("/joke/:{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public Object editJokeById(@PathVariable String id, @RequestBody @Valid JokeEditDTO jokeDTO,
                               BindingResult bindingResult) {
        Optional<Joke> jokeById = this.jokeService.getJokeByIdOptional(id);

        ResponseEntity<Joke> build = checkForAuthor(jokeById);
        if (build != null) return build;

        Object errorRest = getErrors(bindingResult);
        if (errorRest != null) return errorRest;

        Joke joke = this.jokeService.editJoke(jokeById.get(), jokeDTO);
        return ResponseEntity.status(200).body(joke);
    }

    @DeleteMapping("/joke/:{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteJokeById(@PathVariable String id) {
        Optional<Joke> jokeById = this.jokeService.getJokeByIdOptional(id);

        ResponseEntity<Joke> build = checkForAuthor(jokeById);
        if (build != null) return build;

        this.jokeService.deleteJokeById(id);

        return ResponseEntity.status(200).build();
    }

    @GetMapping("/lastTheeJokes")
    @ResponseBody
    public ResponseEntity<List<Joke>> getLastThree() {
        List<Joke> lastThree = this.jokeService.getLastThreeJokes();
        return ResponseEntity.status(200).body(lastThree);
    }


    @GetMapping("/jokes-by-keyword/:{keyword}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Joke>> getJokesByKeyword(@PathVariable String keyword) {
        List<Joke> lastThree = this.jokeService.getJokesByKeyword(keyword);
        return ResponseEntity.status(200).body(lastThree);
    }

    @GetMapping("/jokes-by-most-likes")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Joke>> getJokesByMostLikes() {
        List<Joke> joke = this.jokeService.getJokeByMostLikes();
        return ResponseEntity.status(200).body(joke);
    }

    @PostMapping("/favourite/:{id}/:{username}")
    @IsProfileUser
    public ResponseEntity<?> addFavJoke(@PathVariable String id, @PathVariable String username) {

        return this.favouritesJokeService.addFavouriteJoke(id, username);
    }

    @GetMapping("/favourites/:{username}")
    @IsProfileUser
    public ResponseEntity<?> getAllFavouritesByUsername(@PathVariable String username) {

        return this.favouritesJokeService.getFavouritesJokesByUsername(username);
    }

    @DeleteMapping("/favourite/:{id}/:{username}")
    @ResponseBody
    @IsProfileUser
    public ResponseEntity<?> deleteFavJoke(@PathVariable String id, @PathVariable String username) {

        return this.favouritesJokeService.deleteFavouritesJokeByUsernameAndJokeId(username, id);
    }


    @GetMapping("/find-all")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllJokes() {
        List<Joke> findAll = this.jokeService.findAllJokes();

        return ResponseEntity.status(200).body(findAll);
    }

    @GetMapping("/joke-by-comments/:{username}")
    @ResponseBody
    @IsProfileUser
    public List<Joke> getJokeCommentsByUsername(@PathVariable String username) {
        return this.commentService.getCommentedJokesByUsername(username);
    }

    private ResponseEntity<Joke> checkForAuthor(Optional<Joke> joke) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if (joke.isPresent()) {
            if (!principal.equals(joke.get().getCreator())) {
                return ResponseEntity.status(401).build();
            }
        }
        return null;
    }

}
