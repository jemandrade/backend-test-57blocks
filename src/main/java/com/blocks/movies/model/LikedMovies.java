package com.blocks.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "liked_movies")
@NoArgsConstructor
@AllArgsConstructor
public class LikedMovies {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "movie_id")
    private UUID movieId;

    @Column(name = "user_id")
    private UUID userId;
}
