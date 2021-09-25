package com.blocks.movies.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionSchema {
    private String field;
    private String message;
    private String rejectedValue;
}
