package com.blocks.movies.controller;

import com.blocks.movies.model.Movie;
import com.blocks.movies.model.MovieCreateRequest;
import com.blocks.movies.model.MovieUpdateRequest;
import com.blocks.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestHeader("Authorization") String jwtToken) {
        return ResponseEntity.ok(this.movieService.getAllMovies(jwtToken));
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieCreateRequest movieCreateRequest, @RequestHeader("Authorization") String jwtToken) {
        Movie movie = movieService.createMovie(movieCreateRequest, jwtToken);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") String movieId, @RequestHeader("Authorization") String jwtToken) {
        Movie movie = movieService.getMovieById(movieId, jwtToken);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovieSummary(@RequestBody MovieUpdateRequest movieUpdateRequest, @PathVariable("id") String id, @RequestHeader("Authorization") String jwtToken) {
        Movie movie = movieService.updateMovie(id, movieUpdateRequest.getSummary(), jwtToken);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap> deleteMovieById(@PathVariable("id") String id, @RequestHeader("Authorization") String jwtToken) {
        movieService.deleteMovieById(id, jwtToken);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<HashMap> deleteAllMovies(@RequestHeader("Authorization") String jwtToken) {
        movieService.deleteAllMovies(jwtToken);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<HashMap> likeMovieById(@PathVariable("id") String id, @RequestHeader("Authorization") String jwtToken) {
        movieService.likeMovieById(id, jwtToken);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("liked", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/like")
    public ResponseEntity<List<Movie>> getAllLikedMovies(@RequestHeader("Authorization") String jwtToken) {
        List<Movie> likedMovies = movieService.getAllLikedMoviesByUserId(jwtToken);
        return ResponseEntity.ok(likedMovies);
    }
}
