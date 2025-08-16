package com.joke.quote.service.impl;

import com.joke.common.dto.CreateQuoteRequest;
import com.joke.common.dto.PaginatedResponse;
import com.joke.common.exception.ResourceNotFoundException;
import com.joke.common.model.Quote;
import com.joke.common.repository.QuoteRepository;
import com.joke.quote.service.QuoteService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;

    public QuoteServiceImpl(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public PaginatedResponse<Quote> getAllQuotes(int page, int size) {
        Page<Quote> quotePage = quoteRepository.findAll(Pageable.from(page, size));
        return new PaginatedResponse<>(quotePage.getContent(), page, size, quotePage.getTotalSize());
    }

    @Override
    public Optional<Quote> getQuoteById(String id) {
        return quoteRepository.findById(id);
    }

    @Override
    public List<Quote> getQuotesByCategory(String category) {
        return quoteRepository.findByCategory(category);
    }

    @Override
    public List<Quote> getQuotesByAuthor(String author) {
        return quoteRepository.findByAuthor(author);
    }

    @Override
    public List<Quote> getQuotesByUserId(String userId) {
        return quoteRepository.findByUserId(userId);
    }

    @Override
    public Quote createQuote(CreateQuoteRequest request) {
        Quote quote = new Quote(request.getText(), request.getAuthor(), request.getCategory());
        quote.setUserId(request.getUserId());
        return quoteRepository.save(quote);
    }

    @Override
    public Quote updateQuote(String id, CreateQuoteRequest request) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", id));

        quote.setText(request.getText());
        quote.setAuthor(request.getAuthor());
        quote.setCategory(request.getCategory());
        quote.setUpdatedAt(LocalDateTime.now());

        return quoteRepository.save(quote); // save() handles both create and update
    }

    @Override
    public void deleteQuote(String id) {
        if (quoteRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Quote", id);
        }
        quoteRepository.deleteById(id);
    }

    @Override
    public List<Quote> getRandomQuotes(int limit) {
        return quoteRepository.findRandomQuotes(limit);
    }

    @Override
    public List<Quote> getTopQuotes(int limit) {
        return quoteRepository.findTopByLikes(limit);
    }

    @Override
    public Quote likeQuote(String id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", id));

        quote.setLikes(quote.getLikes() + 1);
        quote.setUpdatedAt(LocalDateTime.now());

        return quoteRepository.save(quote); // save() handles both create and update
    }

    @Override
    public Quote unlikeQuote(String id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote", id));

        if (quote.getLikes() > 0) {
            quote.setLikes(quote.getLikes() - 1);
            quote.setUpdatedAt(LocalDateTime.now());
        }

        return quoteRepository.save(quote); // save() handles both create and update
    }
}