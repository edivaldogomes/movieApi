package com.movie.show.movie.api.repositories;

import com.movie.show.movie.api.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {


}
