package com.blocks.movies.repository;

import com.blocks.movies.model.DomainType;
import com.blocks.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    @Query(value = "select * from movies m where m.user_id = :userId or m.domain_type = 'PUBLIC'", nativeQuery = true)
    List<Movie> findAllIncludingPublicByUserId(@Param("userId") UUID userId);

    Optional<Movie> findByIdAndUserId(UUID id, UUID userId);

    void deleteAllByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    boolean existsByIdAndDomainType(UUID movieId, DomainType domainType);

    @Query(value = "select * from movies m join liked_movies lm on m.id = lm.movie_id where lm.user_id = :userId", nativeQuery = true)
    List<Movie> getAllLikedMoviesByUserId(@Param("userId") UUID userId);
}
