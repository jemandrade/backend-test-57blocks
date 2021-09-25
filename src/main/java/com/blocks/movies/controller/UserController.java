package com.blocks.movies.controller;

import com.blocks.movies.model.MovieUser;
import com.blocks.movies.model.UserCreateResponse;
import com.blocks.movies.model.UserRequest;
import com.blocks.movies.model.UserLoginResponse;
import com.blocks.movies.service.UserService;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserRequest movieUserRequest) {
        MovieUser user = userService.createUser(movieUserRequest);
        return ResponseEntity.ok(mapper.map(user, UserCreateResponse.class));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserLoginResponse> createJwtToken(@RequestBody UserRequest userRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
            final String jwtToken = userService.getJwtToken(userRequest);
            return ResponseEntity.ok(new UserLoginResponse(jwtToken));
        } catch (Exception ex) {
            throw ex;
        }
    }
}
