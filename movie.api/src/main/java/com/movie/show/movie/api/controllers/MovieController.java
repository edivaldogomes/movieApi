package com.movie.show.movie.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.show.movie.api.dtos.MovieDto;
import com.movie.show.movie.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) throws IOException{
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler() throws IOException{
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }



}
