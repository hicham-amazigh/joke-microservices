package com.joke.common.repository;

import com.joke.common.model.Quote;
import io.micronaut.data.mongodb.annotation.MongoAggregateQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;
import java.util.Optional;

@MongoRepository
public interface QuoteRepository extends PageableRepository<Quote, String> {

    List<Quote> findByCategory(String category);

    List<Quote> findByAuthor(String author);

    List<Quote> findByUserId(String userId);

    @MongoAggregateQuery("[{ $sample: { size: :limit } }]")
    List<Quote> findRandomQuotes(int limit);

    @MongoAggregateQuery("[{ $sort: { likes: -1 } }, { $limit: :limit }]")
    List<Quote> findTopByLikes(int limit);

    Optional<Quote> findByTextContaining(String keyword);

    List<Quote> findByAuthorContaining(String authorKeyword);

    List<Quote> findByCategoryAndAuthor(String category, String author);

    long countByUserId(String userId);

    long countByAuthor(String author);

    long countByCategory(String category);
}