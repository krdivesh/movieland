package com.example.application.services;

import com.example.application.data.Movie;
import com.example.application.data.MovieRepository;
import com.example.application.data.pojo.Content;
import com.example.application.data.pojo.GeminiResponse;
import com.example.application.data.pojo.Part;
import com.example.application.data.pojo.GeminiRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class MovieRatingService {
    private final MovieRepository movieRepository;
    @Autowired
    private WebClient webClient;

    public MovieRatingService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public String analyzeMovies() {
        List<Movie> movies = getMovies();
        StringBuilder promptBuilder = new StringBuilder("Below are the movies and how I felt about them");
        for(Movie movie : movies) {
            String title = movie.getTitle();
            String rating = movie.getRating();
            promptBuilder.append(" ").append(title).append(": ").append(rating);
        }
        promptBuilder.append(" tell me briefly what does it tell about me and " +
                "based on this suggest me 5 movies that I should like as a bulleted list. " +
                "No spoilers");


        return callAIModel(promptBuilder.toString());
    }

    public String callAIModel(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key="
                + System.getenv("GEMINI_KEY");

        GeminiRequest payload = new GeminiRequest(List.of(new Content(List.of(new Part(prompt)))));
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String response = webClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .body(Mono.just(objectMapper.writeValueAsString(payload)), String.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            GeminiResponse responseObj = objectMapper.readValue(response, GeminiResponse.class);
            return responseObj.getCandidates().get(0).getContent().getParts().get(0).getText();
        } catch (WebClientResponseException e) {
            // Handle exception
            return "Error: " + e.getResponseBodyAsString();
        } catch (JsonProcessingException e) {
            return "Error: " + e.getMessage();
        }
    }
}
