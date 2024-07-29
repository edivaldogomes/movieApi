package com.movie.show.movie.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.show.movie.api.constants.AppContants;
import com.movie.show.movie.api.dtos.MovieDto;
import com.movie.show.movie.api.dtos.MoviePageResponse;
import com.movie.show.movie.api.exceptions.EmptyFileException;
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
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException, EmptyFileException {

        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty! Please send another file!");
        }
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler() throws IOException {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId, @RequestPart MultipartFile file, @RequestPart String movieDtoObj) throws IOException {
        if (file.isEmpty()) {
            file = null;
        }
        MovieDto movieDto = convertToMovieDto(movieDtoObj);
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieDto, file));
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/allMoviePage")
    public ResponseEntity<MoviePageResponse> getAllMoviePaginationHandler(@RequestParam(defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize) throws IOException {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/allMoviePageSort")
    public ResponseEntity<MoviePageResponse> getAllMoviesWithPaginationAndSorting(@RequestParam(defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(defaultValue = AppContants.SORT_BY, required = false) String sortBy,  @RequestParam(defaultValue = AppContants.SORT_DIR, required = false) String dir) throws IOException {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }
}
