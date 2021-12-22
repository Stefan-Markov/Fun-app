package com.fullstackproject.web;

import com.fullstackproject.errorHandling.ErrorRest;
import com.fullstackproject.errorHandling.MyResourceNotFoundException;
import com.fullstackproject.errorHandling.Success;
import com.fullstackproject.models.dto.UserDto;
import com.fullstackproject.models.entities.User;
import com.fullstackproject.security.rolesAuth.IsAdmin;
import com.fullstackproject.security.rolesAuth.IsProfileUser;
import com.fullstackproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fullstackproject.constants.Constants.API_HOST;

@RestController
@CrossOrigin(API_HOST)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseBody
    public Object register(@RequestBody @Valid User userData, BindingResult bindingResult) {

        Optional<User> tryUser = this.userService.findByUsername(userData.getUsername());

        if (tryUser.isPresent()) {
            ErrorRest errorRest = new ErrorRest();
            errorRest.setMessage("Username is already taken!");
            errorRest.setCause("Username is already taken!");
            errorRest.setCode(401);
            return errorRest;
        }

        ErrorRest errorRest = getErrorRest(bindingResult);
        if (errorRest != null) return errorRest;

        try {
            this.userService.registerUser(userData);
            Success success = new Success();
            success.setCode(200);
            success.setMessage("Successful register!");
            return success;
        } catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Register failed!", exc);
        }
    }

    private ErrorRest getErrorRest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorRest errorRest = new ErrorRest();
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            errorRest.setCode(401);
            for (FieldError e : errors) {
                message.add(e.getDefaultMessage());
            }
            errorRest.setMessage("Update Failed");
            errorRest.setCause(message.toString());
            return errorRest;
        }
        return null;
    }

    @GetMapping("/account/:{username}")
    @ResponseBody
    @IsProfileUser
    public UserDto getUserByUsername(@PathVariable String username) {
        return this.userService.getUserByUsername(username);
    }


    @GetMapping("/:{username}")
    @ResponseBody
    @IsAdmin
    public List<String> getUsersByUsername(@PathVariable String username) {
        return this.userService.findAllBySimilarUsername(username);
    }

    @PutMapping("/role/:{username}")
    @ResponseBody
    @IsAdmin
    public Object removeRoleOnUser(@PathVariable String username) {
        return this.userService.removeRoleOnUser(username);
    }


    @PutMapping("/role-admin/:{username}")
    @ResponseBody
    @IsAdmin
    public Object upgradeRoleOnUser(@PathVariable String username) {
        return this.userService.upgradeRoleOnUser(username);
    }


    @DeleteMapping("/user/:{username}")
    @ResponseBody
    @IsAdmin
    public Object deleteUser(@PathVariable String username) {
        return this.userService.deleteUser(username);
    }
}
