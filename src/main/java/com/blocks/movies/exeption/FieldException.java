package com.blocks.movies.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldException extends RuntimeException{
    private final String field;
    private final String message;
    private final String rejectedValue;
}
