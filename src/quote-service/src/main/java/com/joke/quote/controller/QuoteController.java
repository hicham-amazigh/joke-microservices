package com.joke.quote.controller;

import com.joke.common.dto.ApiResponse;
import com.joke.common.dto.CreateQuoteRequest;
import com.joke.common.dto.PaginatedResponse;
import com.joke.common.model.Quote;
import com.joke.quote.service.QuoteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

@Controller("/api/quotes")
@Validated
@ExecuteOn(TaskExecutors.IO)
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Get
    public HttpResponse<ApiResponse<PaginatedResponse<Quote>>> getAllQuotes(
            @QueryValue(defaultValue = "0") @Min(0) int page,
            @QueryValue(defaultValue = "10") @Min(1) @Max(100) int size) {

        PaginatedResponse<Quote> quotes = quoteService.getAllQuotes(page, size);
        return HttpResponse.ok(ApiResponse.success(quotes));
    }

    @Get("/{id}")
    public HttpResponse<ApiResponse<Quote>> getQuoteById(@PathVariable String id) {
        return quoteService.getQuoteById(id)
                .map(quote -> HttpResponse.ok(ApiResponse.success(quote)))
                .orElse(HttpResponse.notFound());
    }

    @Get("/category/{category}")
    public HttpResponse<ApiResponse<List<Quote>>> getQuotesByCategory(@PathVariable String category) {
        List<Quote> quotes = quoteService.getQuotesByCategory(category);
        return HttpResponse.ok(ApiResponse.success(quotes));
    }

    @Get("/author/{author}")
    public HttpResponse<ApiResponse<List<Quote>>> getQuotesByAuthor(@PathVariable String author) {
        List<Quote> quotes = quoteService.getQuotesByAuthor(author);
        return HttpResponse.ok(ApiResponse.success(quotes));
    }

    @Get("/user/{userId}")
    public HttpResponse<ApiResponse<List<Quote>>> getQuotesByUserId(@PathVariable String userId) {
        List<Quote> quotes = quoteService.getQuotesByUserId(userId);
        return HttpResponse.ok(ApiResponse.success(quotes));
    }

    @Get("/random")
    public HttpResponse<ApiResponse<List<Quote>>> getRandomQuotes(
            @QueryValue(defaultValue = "5") @Min(1) @Max(20) int limit) {

        List<Quote> quotes = quoteService.getRandomQuotes(limit);
        return HttpResponse.ok(ApiResponse.success(quotes));
    }

    @Get("/top")
    public HttpResponse<ApiResponse<List<Quote>>> getTopQuotes(
            @QueryValue(defaultValue = "10") @Min(1) @Max(50) int limit) {

        List<Quote> quotes = quoteService.getTopQuotes(limit);
        return HttpResponse.ok(ApiResponse.success(quotes));
    }

    @Post
    public HttpResponse<ApiResponse<Quote>> createQuote(@Body @Valid CreateQuoteRequest request) {
        Quote quote = quoteService.createQuote(request);
        return HttpResponse.created(ApiResponse.success("Quote created successfully", quote));
    }

    @Put("/{id}")
    public HttpResponse<ApiResponse<Quote>> updateQuote(
            @PathVariable String id,
            @Body @Valid CreateQuoteRequest request) {

        Quote quote = quoteService.updateQuote(id, request);
        return HttpResponse.ok(ApiResponse.success("Quote updated successfully", quote));
    }

    @Delete("/{id}")
    public HttpResponse<ApiResponse<Void>> deleteQuote(@PathVariable String id) {
        quoteService.deleteQuote(id);
        return HttpResponse.ok(ApiResponse.success("Quote deleted successfully", null));
    }

    @Post("/{id}/like")
    public HttpResponse<ApiResponse<Quote>> likeQuote(@PathVariable String id) {
        Quote quote = quoteService.likeQuote(id);
        return HttpResponse.ok(ApiResponse.success("Quote liked", quote));
    }

    @Post("/{id}/unlike")
    public HttpResponse<ApiResponse<Quote>> unlikeQuote(@PathVariable String id) {
        Quote quote = quoteService.unlikeQuote(id);
        return HttpResponse.ok(ApiResponse.success("Quote unliked", quote));
    }
}