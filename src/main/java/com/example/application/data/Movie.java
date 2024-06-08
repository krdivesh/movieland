package com.example.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    private Long id;
    private String title;
    private String director;
    private String releaseYear;
    private String genre;
}
