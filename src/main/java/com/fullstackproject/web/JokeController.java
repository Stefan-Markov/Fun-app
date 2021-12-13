package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.models.entities.*;
import com.fullstackproject.models.dto.JokeDTO;
import com.fullstackproject.models.dto.JokeEditDTO;
import com.fullstackproject.repositories.FavouritesJokeRepository;
import com.fullstackproject.repositories.JokeRepository;
import com.fullstackproject.repositories.UserRepository;
import com.fullstackproject.security.rolesAuth.IsProfileUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fullstackproject.constants.Constants.API_HOST;

@RestController
@Transactional
@CrossOrigin(API_HOST)
public class JokeController {

    private final UserRepository userRepository;
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;
    private final FavouritesJokeRepository favouritesJokeRepository;

    public JokeController(UserRepository userRepository, JokeRepository jokeRepository,
                          ModelMapper modelMapper, FavouritesJokeRepository favouritesJokeRepository) {
        this.userRepository = userRepository;
        this.jokeRepository = jokeRepository;
        this.modelMapper = modelMapper;
        this.favouritesJokeRepository = favouritesJokeRepository;
    }


    @PostMapping("/joke")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public Object createJoke(@RequestBody @Valid JokeDTO jokeData, BindingResult bindingResult) {

        Optional<User> user = this.userRepository.findByUsername(jokeData.getUsername());

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

        Joke joke = this.modelMapper.map(jokeData, Joke.class);

        joke.setUser(user.get());
        joke.setCreator(user.get().getUsername());
        joke.setCreatedDate(LocalDateTime.now());

        this.jokeRepository.save(joke);
        return ResponseEntity.status(200).body(joke);
    }

    @GetMapping("/joke-manage/:{username}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    @IsProfileUser
    public ResponseEntity<List<Joke>> getJokes(@PathVariable String username) {
        List<Joke> jokes = this.jokeRepository.findAllByUsername(username);
        return ResponseEntity.status(200).body(jokes);
    }


    @GetMapping("/joke/:{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Joke> getJokeById(@PathVariable String id) {
        Optional<Joke> joke = this.jokeRepository.findById(id);
        List<Comment> collect = joke.get().getComments()
                .stream().sorted(Comparator.comparing(Comment::getLocalDate))
                .collect(Collectors.toList());
        joke.get().setComments(collect);

        return ResponseEntity.status(200).body(joke.get());
    }

    @PutMapping("/joke/:{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public Object editJokeById(@PathVariable String id, @RequestBody @Valid JokeEditDTO jokeDTO, BindingResult bindingResult) {
        Optional<Joke> jokeById = this.jokeRepository.findById(id);

        if (bindingResult.hasErrors()) {
            ErrorRest errorRest = new ErrorRest();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            errorRest.setCode(401);
            for (FieldError e : errors) {
//                message.add(e.getField() + " : " + e.getDefaultMessage());
                message.add(e.getDefaultMessage());
            }
            errorRest.setMessage("Create of joke Failed");
            errorRest.setCause(message.toString());
            return errorRest;
        }

        jokeById.get().setContent(jokeDTO.getContent());
        jokeById.get().setKeyword(jokeDTO.getKeyword());
        jokeById.get().setTitle(jokeDTO.getTitle());

        this.jokeRepository.save(jokeById.get());
        return ResponseEntity.status(200).body(jokeById.get());
    }

    @DeleteMapping("/joke/:{id}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteJokeById(@PathVariable String id) {

        this.jokeRepository.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @GetMapping("/lastTheeJokes")
    @ResponseBody
    public ResponseEntity<List<Joke>> getLastThree() {
        List<Joke> lastThree = this.jokeRepository.findLastThree()
                .stream()
                .sorted(Comparator.comparing(Joke::getCreatedDate).reversed())
                .limit(3).collect(Collectors.toList());
        return ResponseEntity.status(200).body(lastThree);
    }


    @GetMapping("/jokes-by-keyword/:{keyword}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Joke>> getJokesByKeyword(@PathVariable String keyword) {
        List<Joke> lastThree = this.jokeRepository.findAllByKeyword(keyword)
                .stream()
                .sorted(Comparator.comparing(Joke::getCreatedDate))
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(lastThree);
    }

    @GetMapping("/jokes-by-most-likes")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Joke>> getJokesByMostLikes() {
        List<Joke> joke = this.jokeRepository.findJokeWithMostLikes()
                .stream().limit(1).collect(Collectors.toList());
        return ResponseEntity.status(200).body(joke);
    }

    @PostMapping("/favourite/:{id}/:{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addFavJoke(@PathVariable String id, @PathVariable String username) {

        Favourites already = this.favouritesJokeRepository.findByUsernameAndId(username, id);

        if (already != null) {
            return ResponseEntity.status(200).build();
        }

        Favourites favourites = new Favourites();
        favourites.setUsername(username);
        favourites.setJokeId(id);

        this.favouritesJokeRepository.save(favourites);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/favourites/:{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllFavouritesByUsername(@PathVariable String username) {

        List<String> jokeIds = this.favouritesJokeRepository.findAllByUsername(username);
        List<Joke> jokes = new ArrayList<>();

        if (jokeIds.size() == 0) {
            return ResponseEntity.status(200).build();
        }

        for (String id : jokeIds) {
            Joke joke = this.jokeRepository.findById(id).get();
            jokes.add(joke);
        }
        return ResponseEntity.status(200).body(jokes);
    }

    @DeleteMapping("/favourite/:{id}/:{username}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteFavJoke(@PathVariable String id, @PathVariable String username) {

        Favourites already = this.favouritesJokeRepository.findByUsernameAndId(username, id);

        if (already != null) {
            this.favouritesJokeRepository.delete(already);
            return ResponseEntity.status(200).body(already);
        }

        return ResponseEntity.status(200).body(0);
    }


    @GetMapping("/find-all")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAllJokes() {
        List<Joke> findAll = this.jokeRepository.findAll();

        return ResponseEntity.status(200).body(findAll);
    }
}
