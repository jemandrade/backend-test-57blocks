package com.blocks.movies.service;

import com.blocks.movies.exeption.FieldException;
import com.blocks.movies.model.*;
import com.blocks.movies.repository.LikedMoviesRepository;
import com.blocks.movies.repository.MovieRepository;
import com.blocks.movies.util.JwtUtil;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final LikedMoviesRepository likedMoviesRepository;
    private final JwtUtil jwtUtil;
    private final Mapper mapper;

    public List<Movie> getAllMovies(String jwtToken) {
        List<Movie> allMovies = movieRepository.findAllIncludingPublicByUserId(getUserId(jwtToken));
        return allMovies;
    }

    @Transactional
    public Movie createMovie(MovieCreateRequest movieCreateRequest, String jwtToken) {
        Movie movie = mapper.map(movieCreateRequest, Movie.class);
        movie.setUserId(getUserId(jwtToken));
        movie.setDomainType(DomainType.PRIVATE);
        movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public Movie getMovieById(String movieId, String jwtToken) {
        Movie movie = movieRepository.findByIdAndUserId(UUID.fromString(movieId), getUserId(jwtToken))
                .orElseThrow(() -> new FieldException("movie_id", "Movie not found or is not available for this user", movieId));

        return movie;
    }

    public UUID getUserId(String jwtToken) {
        String userId = jwtUtil.extractUserId(jwtToken.replace("Bearer ",""));
        return UUID.fromString(userId);
    }

    @Transactional
    public Movie updateMovie(String movieId, String updatedMovieDescription, String jwtToken) {
        Movie movie = getMovieById(movieId, jwtToken);
        movie.setSummary(updatedMovieDescription);
        movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public void deleteMovieById(String movieId, String jwtToken) {
        Movie movie = getMovieById(movieId, jwtToken);
        movieRepository.deleteById(movie.getId());
    }

    @Transactional
    public void deleteAllMovies(String jwtToken) {
        boolean hasMovies = movieRepository.existsByUserId(getUserId(jwtToken));
        if (hasMovies) {
            movieRepository.deleteAllByUserId(getUserId(jwtToken));
        } else {
            throw new FieldException("user_id", "This user doesn't have private movies", jwtToken);
        }
    }

    @Transactional
    public void likeMovieById(String movieId, String jwtToken) {
        boolean isPublicMovie = movieRepository.existsByIdAndDomainType(UUID.fromString(movieId), DomainType.PUBLIC);
        boolean movieAlreadyLiked = likedMoviesRepository.existsByMovieIdAndUserId(UUID.fromString(movieId), getUserId(jwtToken));

        if (movieAlreadyLiked) {
            throw new FieldException("movie_id", "You already liked this movie", movieId);
        }

        if (isPublicMovie) {
            LikedMovies likedMovie = new LikedMovies();
            likedMovie.setMovieId(UUID.fromString(movieId));
            likedMovie.setUserId(getUserId(jwtToken));
            likedMoviesRepository.save(likedMovie);
        } else {
            throw new FieldException("movie_id", "The movie doesn't exist or is private", movieId);
        }
    }

    @Transactional
    public List<Movie> getAllLikedMoviesByUserId(String jwtToken) {
        List<Movie> likedMovies = movieRepository.getAllLikedMoviesByUserId(getUserId(jwtToken));
        return likedMovies;
    }
}
