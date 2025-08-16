package com.joke.common.repository;

import com.joke.common.model.Joke;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;
import java.util.Optional;

@MongoRepository
public interface JokeRepository extends PageableRepository<Joke, String> {

    List<Joke> findByCategory(String category);

    List<Joke> findByUserId(String userId);

    List<Joke> findByIsApproved(boolean approved);

    List<Joke> findByOrderByLikesDesc();

    @Query("{ $sample: { size: ?0 } }")
    List<Joke> findRandomJokes(int limit);

    List<Joke> findByIsApprovedTrueOrderByLikesDesc();

    List<Joke> findTop10ByIsApprovedTrueOrderByLikesDesc();

    @Query("[ { $match: { isApproved: true } }, { $sort: { likes: -1 } }, { $limit: ?0 } ]")
    List<Joke> findTopApprovedByLikes(int limit);

    Optional<Joke> findBySetupContaining(String keyword);

    List<Joke> findByCategoryAndIsApproved(String category, boolean approved);

    long countByUserId(String userId);

    long countByIsApproved(boolean approved);
}