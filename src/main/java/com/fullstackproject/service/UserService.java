package com.fullstackproject.service;

import com.fullstackproject.errorHandling.Success;
import com.fullstackproject.models.dto.UserDto;
import com.fullstackproject.models.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserService {

    Optional<User> findByUsername(String username);

    void registerUser(User user);

    UserDto getUserByUsername(String username);

    List<String> findAllBySimilarUsername(String username) throws ExecutionException, InterruptedException;

    Success removeRoleOnUser(String username);

    Success upgradeRoleOnUser(String username);

    Success deleteUser(String username);
}
