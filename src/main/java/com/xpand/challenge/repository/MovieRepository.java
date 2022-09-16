package com.xpand.challenge.repository;

import com.xpand.challenge.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT m FROM Movie m JOIN FETCH m.actors WHERE m IN :movies")
    List<Movie> findMoviesActors(List<Movie> movies);

    List<Movie> findMoviesByDate(LocalDate date);
}
