package com.example.application.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Autowired
    public DatabaseInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() != 0) {
            return;
        }

        movieRepository.save(new Movie(1L, "The Shawshank Redemption", "Frank Darabont", "1994", "Drama"));
        movieRepository.save(new Movie(2L, "The Godfather", "Francis Ford Coppola", "1972", "Crime"));
        movieRepository.save(new Movie(3L, "The Dark Knight", "Christopher Nolan", "2008", "Action"));
        movieRepository.save(new Movie(4L, "Pulp Fiction", "Quentin Tarantino", "1994", "Crime"));
        movieRepository.save(new Movie(5L, "Schindler's List", "Steven Spielberg", "1993", "Biography"));
    }
}
