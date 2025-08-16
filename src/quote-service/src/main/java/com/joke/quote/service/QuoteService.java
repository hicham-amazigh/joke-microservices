package com.joke.quote.service;

import com.joke.common.dto.CreateQuoteRequest;
import com.joke.common.dto.PaginatedResponse;
import com.joke.common.model.Quote;

import java.util.List;
import java.util.Optional;

public interface QuoteService {
    PaginatedResponse<Quote> getAllQuotes(int page, int size);
    Optional<Quote> getQuoteById(String id); // Changed from Long to String
    List<Quote> getQuotesByCategory(String category);
    List<Quote> getQuotesByAuthor(String author);
    List<Quote> getQuotesByUserId(String userId); // Changed from Long to String
    Quote createQuote(CreateQuoteRequest request);
    Quote updateQuote(String id, CreateQuoteRequest request); // Changed from Long to String
    void deleteQuote(String id); // Changed from Long to String
    List<Quote> getRandomQuotes(int limit);
    List<Quote> getTopQuotes(int limit);
    Quote likeQuote(String id); // Changed from Long to String
    Quote unlikeQuote(String id); // Changed from Long to String
}
