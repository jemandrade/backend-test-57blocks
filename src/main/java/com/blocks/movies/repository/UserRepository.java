package com.blocks.movies.repository;

import com.blocks.movies.model.MovieUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<MovieUser, UUID> {
    Optional<MovieUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
