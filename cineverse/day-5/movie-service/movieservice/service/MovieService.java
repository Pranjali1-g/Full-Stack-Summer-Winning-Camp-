package com.cineverse.movieservice.service;

import com.cineverse.movieservice.model.Movie;
import com.cineverse.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> getAllMovies(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> searchMovies(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie profile record not located."));
    }

    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }
}