package com.joke.joke.controller;

import com.joke.common.dto.ApiResponse;
import com.joke.common.dto.CreateJokeRequest;
import com.joke.common.dto.PaginatedResponse;
import com.joke.common.model.Joke;
import com.joke.joke.service.JokeService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

@Controller("/api/jokes")
@Validated
public class JokeController {
    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @Get
    public HttpResponse<ApiResponse<PaginatedResponse<Joke>>> getAllJokes(
            @QueryValue(defaultValue = "0") @Min(0) int page,
            @QueryValue(defaultValue = "10") @Min(1) @Max(100) int size,
            @QueryValue(defaultValue = "true") boolean approvedOnly) {

        PaginatedResponse<Joke> jokes = approvedOnly ?
                jokeService.getApprovedJokes(page, size) :
                jokeService.getAllJokes(page, size);

        return HttpResponse.ok(ApiResponse.success(jokes));
    }

    @Get("/{id}")
    public HttpResponse<ApiResponse<Joke>> getJokeById(@PathVariable String id) {
        return jokeService.getJokeById(id)
                .map(joke -> HttpResponse.ok(ApiResponse.success(joke)))
                .orElse(HttpResponse.notFound());
    }

    @Get("/category/{category}")
    public HttpResponse<ApiResponse<List<Joke>>> getJokesByCategory(@PathVariable String category) {
        List<Joke> jokes = jokeService.getJokesByCategory(category);
        return HttpResponse.ok(ApiResponse.success(jokes));
    }

    @Get("/user/{userId}")
    public HttpResponse<ApiResponse<List<Joke>>> getJokesByUserId(@PathVariable String userId) {
        List<Joke> jokes = jokeService.getJokesByUserId(userId);
        return HttpResponse.ok(ApiResponse.success(jokes));
    }

    @Get("/random")
    public HttpResponse<ApiResponse<List<Joke>>> getRandomJokes(
            @QueryValue(defaultValue = "5") @Min(1) @Max(20) int limit) {

        List<Joke> jokes = jokeService.getRandomJokes(limit);
        return HttpResponse.ok(ApiResponse.success(jokes));
    }

    @Get("/top")
    public HttpResponse<ApiResponse<List<Joke>>> getTopJokes(
            @QueryValue(defaultValue = "10") @Min(1) @Max(50) int limit) {

        List<Joke> jokes = jokeService.getTopJokes(limit);
        return HttpResponse.ok(ApiResponse.success(jokes));
    }

    @Get("/pending")
    public HttpResponse<ApiResponse<List<Joke>>> getPendingJokes() {
        List<Joke> jokes = jokeService.getPendingJokes();
        return HttpResponse.ok(ApiResponse.success(jokes));
    }

    @Post
    public HttpResponse<ApiResponse<Joke>> createJoke(@Body @Valid CreateJokeRequest request) {
        Joke joke = jokeService.createJoke(request);
        return HttpResponse.created(ApiResponse.success("Joke created successfully (pending approval)", joke));
    }

    @Put("/{id}")
    public HttpResponse<ApiResponse<Joke>> updateJoke(
            @PathVariable String id,
            @Body @Valid CreateJokeRequest request) {

        Joke joke = jokeService.updateJoke(id, request);
        return HttpResponse.ok(ApiResponse.success("Joke updated successfully", joke));
    }

    @Delete("/{id}")
    public HttpResponse<ApiResponse<Void>> deleteJoke(@PathVariable String id) {
        jokeService.deleteJoke(id);
        return HttpResponse.ok(ApiResponse.success("Joke deleted successfully", null));
    }

    @Post("/{id}/like")
    public HttpResponse<ApiResponse<Joke>> likeJoke(@PathVariable String id) {
        Joke joke = jokeService.likeJoke(id);
        return HttpResponse.ok(ApiResponse.success("Joke liked", joke));
    }

    @Post("/{id}/unlike")
    public HttpResponse<ApiResponse<Joke>> unlikeJoke(@PathVariable String id) {
        Joke joke = jokeService.unlikeJoke(id);
        return HttpResponse.ok(ApiResponse.success("Joke unliked", joke));
    }

    @Post("/{id}/approve")
    public HttpResponse<ApiResponse<Joke>> approveJoke(@PathVariable String id) {
        Joke joke = jokeService.approveJoke(id);
        return HttpResponse.ok(ApiResponse.success("Joke approved", joke));
    }

    @Post("/{id}/reject")
    public HttpResponse<ApiResponse<Joke>> rejectJoke(@PathVariable String id) {
        Joke joke = jokeService.rejectJoke(id);
        return HttpResponse.ok(ApiResponse.success("Joke rejected", joke));
    }
}