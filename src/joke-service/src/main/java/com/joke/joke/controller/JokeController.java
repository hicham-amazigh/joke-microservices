package com.joke.joke.controller;

import com.joke.common.model.Joke;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Singleton;

import java.util.List;

@Controller("/api/joke")
public class JokeController {

    private final List<String> jokesText = List.of(
            "Why don't skeletons fight each other? They don't have the guts.",
            "Why did the bicycle fall over? It was two-tired.",
            "I told my computer I needed a break, and it froze."
    );

    @Get
    public HttpResponse<List<Joke>> getJokes() {
        List<Joke> jokes = jokesText.stream()
                .map(Joke::new)
                .toList();

        return HttpResponse.ok(jokes);
    }
}
