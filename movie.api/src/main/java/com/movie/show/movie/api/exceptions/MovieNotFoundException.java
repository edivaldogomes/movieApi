package com.movie.show.movie.api.exceptions;

public class MovieNotFoundException extends  RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }
}