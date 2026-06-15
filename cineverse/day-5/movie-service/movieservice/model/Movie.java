package com.cineverse.movieservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String id; // MongoDB matches alphanumeric string ObjectIds seamlessly
    private String title;
    private List<String> genre;
    private Double rating;
    private String language;
    private Integer duration;
    private String releaseDate;
    private String posterUrl;
}