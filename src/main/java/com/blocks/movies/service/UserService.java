package com.blocks.movies.service;

import com.blocks.movies.exeption.FieldException;
import com.blocks.movies.model.MovieUser;
import com.blocks.movies.model.UserRequest;
import com.blocks.movies.repository.UserRepository;
import com.blocks.movies.util.JwtUtil;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public MovieUser createUser(UserRequest userRequest){
        MovieUser movieUser = mapper.map(userRequest, MovieUser.class);
        validateCredentialsFormat(movieUser);
        userRepository.save(movieUser);
        return movieUser;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        validateEmailFormat(email);
        MovieUser movieUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new FieldException("email", "This email is not registered", email)
                );
        return new User(movieUser.getEmail(), movieUser.getPassword(), new ArrayList<>());
    }

    @Transactional
    public MovieUser loadUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public String getJwtToken(UserRequest userRequest) {
        MovieUser user = loadUserByEmail(userRequest.getEmail());
        return jwtUtil.generateToken(user);
    }

    @Transactional
    public MovieUser loadUserById(String id) {
        return userRepository.findById(UUID.fromString(id)).get();
    }

    public void validateEmailFormat(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new FieldException("email", "Invalid email format", email);
        }
    }

    public void validatePasswordFormat(String password) {
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[!@#?\\]]).{10,100}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new FieldException("password", "Invalid password - it should contain at least 10 characters, one lowercase letter," +
                    " one uppercase letter and one of the following characters: !, @, #, ?, or ]", password);
        }
    }

    public void validateCredentialsFormat(MovieUser movieUser) {
        validateEmailFormat(movieUser.getEmail());
        validatePasswordFormat(movieUser.getPassword());

        boolean emailRegistered = userRepository.existsByEmail(movieUser.getEmail());
        if (emailRegistered) {
            throw new FieldException("email", "There's already an account with this email", movieUser.getEmail());
        }
    }
}
