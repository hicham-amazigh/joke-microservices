package com.joke.joke.service.impl;

import com.joke.common.dto.CreateUserRequest;
import com.joke.common.exception.DuplicateResourceException;
import com.joke.common.exception.ResourceNotFoundException;
import com.joke.common.model.User;
import com.joke.common.repository.UserRepository;
import com.joke.joke.service.UserService;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(CreateUserRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("User", "username", request.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }

        User user = new User(request.getUsername(), request.getEmail(), request.getDisplayName());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String id, CreateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User: " + id));

        // Check if new username conflicts with another user
        Optional<User> existingUserByUsername = userRepository.findByUsername(request.getUsername());
        if (existingUserByUsername.isPresent() && !existingUserByUsername.get().getId().equals(id)) {
            throw new DuplicateResourceException("User", "username", request.getUsername());
        }

        // Check if new email conflicts with another user
        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getEmail());
        if (existingUserByEmail.isPresent() && !existingUserByEmail.get().getId().equals(id)) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setDisplayName(request.getDisplayName());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user); // save() handles both create and update
    }

    @Override
    public void deleteUser(String id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}