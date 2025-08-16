package com.joke.joke.service;

import com.joke.common.dto.CreateUserRequest;
import com.joke.common.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(String id); // Changed from Long to String
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User createUser(CreateUserRequest request);
    User updateUser(String id, CreateUserRequest request); // Changed from Long to String
    void deleteUser(String id); // Changed from Long to String
    long getUserCount();
}
