package com.example.application.services;

import com.example.application.data.Movie;
import com.example.application.data.MovieRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class MovieRatingService {
    private final MovieRepository movieRepository;

    public MovieRatingService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
