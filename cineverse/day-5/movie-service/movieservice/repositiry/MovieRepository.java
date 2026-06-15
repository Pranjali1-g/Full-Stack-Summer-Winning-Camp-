package com.cineverse.movieservice.repository;

import com.cineverse.movieservice.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    
    // Executes a safe, case-insensitive keyword regex match evaluation over targeted title inputs
    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}