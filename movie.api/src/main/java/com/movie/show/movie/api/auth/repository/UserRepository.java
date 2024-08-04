package com.movie.show.movie.api.auth.repository;

import com.movie.show.movie.api.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
