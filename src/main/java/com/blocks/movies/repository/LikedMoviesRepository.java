package com.blocks.movies.repository;

import com.blocks.movies.model.LikedMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikedMoviesRepository extends JpaRepository<LikedMovies, UUID> {
    boolean existsByMovieIdAndUserId(UUID movieId, UUID userId);
}
