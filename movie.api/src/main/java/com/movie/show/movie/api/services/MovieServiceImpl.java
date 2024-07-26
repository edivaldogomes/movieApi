package com.movie.show.movie.api.services;

import com.movie.show.movie.api.dtos.MovieDto;
import com.movie.show.movie.api.entities.Movie;
import com.movie.show.movie.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. upload the file
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new RuntimeException("File already exists! Please enter another file name!");
        }
        String uploadedFileName = fileService.uploadFile(path, file);

        // 2. Set the value field 'poster' as filename
        movieDto.setPoster(uploadedFileName);

        // 3. Map dto to Movie object
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // 4. Save the movie Object -> saved Movie object
        Movie savedMovie = movieRepository.save(movie);

        // 5. Generate de posterUrl
        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        // 6. Map Movie object to DTO object and return it
        MovieDto response = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) throws IOException {
        // 1. Check the data in BD and if exists, fetch the data of given ID
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        // 2. Generate posterUrl
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        // 3. Map to MovieDto object and return
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() throws IOException {
        // 1. fetch all data from DB
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();

        // 2. Iterate through the list, generate portUrl for each movie obj.
        // and map to MovieDto obj
        for (Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }

        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {

        return null;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        return "";
    }
}
