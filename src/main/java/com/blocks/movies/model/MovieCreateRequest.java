package com.blocks.movies.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieCreateRequest {
    private String name;
    private int length;
    private Language language;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
}
