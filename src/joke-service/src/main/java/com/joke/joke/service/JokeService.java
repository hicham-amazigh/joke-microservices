package com.joke.joke.service;

import com.joke.common.dto.CreateJokeRequest;
import com.joke.common.dto.PaginatedResponse;
import com.joke.common.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeService {
    PaginatedResponse<Joke> getAllJokes(int page, int size);
    PaginatedResponse<Joke> getApprovedJokes(int page, int size);
    Optional<Joke> getJokeById(String id); // Changed from Long to String
    List<Joke> getJokesByCategory(String category);
    List<Joke> getJokesByUserId(String userId); // Changed from Long to String
    Joke createJoke(CreateJokeRequest request);
    Joke updateJoke(String id, CreateJokeRequest request); // Changed from Long to String
    void deleteJoke(String id); // Changed from Long to String
    List<Joke> getRandomJokes(int limit);
    List<Joke> getTopJokes(int limit);
    Joke likeJoke(String id); // Changed from Long to String
    Joke unlikeJoke(String id); // Changed from Long to String
    Joke approveJoke(String id); // Changed from Long to String
    Joke rejectJoke(String id); // Changed from Long to String
    List<Joke> getPendingJokes();
}
