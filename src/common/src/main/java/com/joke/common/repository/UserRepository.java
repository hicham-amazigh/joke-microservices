package com.joke.common.repository;

import com.joke.common.model.User;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@MongoRepository
public interface UserRepository extends CrudRepository<User, String> {

    // Basic CRUD operations are inherited from CrudRepository
    // findAll(), findById(), save(), deleteById(), count() are auto-implemented

    // Custom finder methods - auto-implemented by Micronaut Data
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Additional useful methods
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);
}