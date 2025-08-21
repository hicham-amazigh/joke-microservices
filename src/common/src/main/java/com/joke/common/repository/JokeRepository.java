package com.joke.common.repository;

import com.joke.common.model.Joke;
import io.micronaut.data.mongodb.annotation.MongoAggregateQuery;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;
import java.util.Optional;

@MongoRepository
public interface JokeRepository extends PageableRepository<Joke, String> {

    List<Joke> findByCategory(String category);

    List<Joke> findByUserId(String userId);

    List<Joke> findByIsApproved(boolean approved);

    @MongoAggregateQuery("[{ $sample: { size: :limit } }]")
    List<Joke> findRandomJokes(int limit);

    List<Joke> findByIsApprovedTrueOrderByLikesDesc();

    List<Joke> findTop10ByIsApprovedTrueOrderByLikesDesc();

    @MongoAggregateQuery("[{ $match: { isApproved: true } }, { $sort: { likes: -1 } }, { $limit: :limit }]")
    List<Joke> findTopApprovedByLikes(int limit);

    Optional<Joke> findBySetupContaining(String keyword);

    List<Joke> findByCategoryAndIsApproved(String category, boolean approved);

    long countByUserId(String userId);

    long countByIsApproved(boolean approved);
}