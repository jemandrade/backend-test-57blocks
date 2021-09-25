package com.blocks.movies.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserCreateResponse {
    private UUID id;
    private String email;
}
