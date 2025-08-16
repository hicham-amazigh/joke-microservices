package com.joke.joke.service.impl;

import com.joke.common.dto.CreateJokeRequest;
import com.joke.common.dto.PaginatedResponse;
import com.joke.common.exception.ResourceNotFoundException;
import com.joke.common.model.Joke;
import com.joke.common.repository.JokeRepository;
import com.joke.joke.service.JokeService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;

    public JokeServiceImpl(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public PaginatedResponse<Joke> getAllJokes(int page, int size) {
        Page<Joke> jokePage = jokeRepository.findAll(Pageable.from(page, size));
        return new PaginatedResponse<>(jokePage.getContent(), page, size, jokePage.getTotalSize());
    }

    @Override
    public PaginatedResponse<Joke> getApprovedJokes(int page, int size) {
        List<Joke> approvedJokes = jokeRepository.findByIsApproved(true);
        List<Joke> pagedJokes = approvedJokes.stream()
                .sorted((j1, j2) -> j2.getCreatedAt().compareTo(j1.getCreatedAt()))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(pagedJokes, page, size, approvedJokes.size());
    }

    @Override
    public Optional<Joke> getJokeById(String id) {
        return jokeRepository.findById(id);
    }

    @Override
    public List<Joke> getJokesByCategory(String category) {
        return jokeRepository.findByCategoryAndIsApproved(category, true);
    }

    @Override
    public List<Joke> getJokesByUserId(String userId) {
        return jokeRepository.findByUserId(userId);
    }

    @Override
    public Joke createJoke(CreateJokeRequest request) {
        Joke joke = new Joke(request.getSetup(), request.getPunchline(), request.getCategory());
        joke.setUserId(request.getUserId());
        // New jokes need approval by default
        joke.setApproved(false);
        return jokeRepository.save(joke);
    }

    @Override
    public Joke updateJoke(String id, CreateJokeRequest request) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Joke: " + id));

        joke.setSetup(request.getSetup());
        joke.setPunchline(request.getPunchline());
        joke.setCategory(request.getCategory());
        joke.setUpdatedAt(LocalDateTime.now());

        return jokeRepository.save(joke); // save() handles both create and update
    }

    @Override
    public void deleteJoke(String id) {
        if (jokeRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Joke: " + id);
        }
        jokeRepository.deleteById(id);
    }

    @Override
    public List<Joke> getRandomJokes(int limit) {
        return jokeRepository.findRandomJokes(limit);
    }

    @Override
    public List<Joke> getTopJokes(int limit) {
        return jokeRepository.findTopApprovedByLikes(limit);
    }

    @Override
    public Joke likeJoke(String id) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Joke: " + id));

        joke.setLikes(joke.getLikes() + 1);
        joke.setUpdatedAt(LocalDateTime.now());

        return jokeRepository.save(joke); // save() handles both create and update
    }

    @Override
    public Joke unlikeJoke(String id) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Joke: " + id));

        if (joke.getLikes() > 0) {
            joke.setLikes(joke.getLikes() - 1);
            joke.setUpdatedAt(LocalDateTime.now());
        }

        return jokeRepository.save(joke); // save() handles both create and update
    }

    @Override
    public Joke approveJoke(String id) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Joke: " + id));

        joke.setApproved(true);
        joke.setUpdatedAt(LocalDateTime.now());

        return jokeRepository.save(joke); // save() handles both create and update
    }

    @Override
    public Joke rejectJoke(String id) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Joke: " + id));

        joke.setApproved(false);
        joke.setUpdatedAt(LocalDateTime.now());

        return jokeRepository.save(joke); // save() handles both create and update
    }

    @Override
    public List<Joke> getPendingJokes() {
        return jokeRepository.findByIsApproved(false);
    }
}