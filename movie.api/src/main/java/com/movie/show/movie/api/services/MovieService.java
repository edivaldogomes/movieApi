package com.movie.show.movie.api.services;

import com.movie.show.movie.api.dtos.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId) throws IOException;

    List<MovieDto> getAllMovies() throws IOException;

    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException;

    String deleteMovie(Integer movieId) throws IOException;
}