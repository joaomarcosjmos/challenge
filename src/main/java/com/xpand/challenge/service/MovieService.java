package com.xpand.challenge.service;

import java.time.LocalDate;
import java.util.List;

import com.xpand.challenge.dto.IdentifiableMovieDTO;
import com.xpand.challenge.dto.MovieDTO;

public interface MovieService {
    
    IdentifiableMovieDTO createMovie(MovieDTO movieDTO);

    IdentifiableMovieDTO getMovie(Long id);

    List<IdentifiableMovieDTO> getMovies();

    List<IdentifiableMovieDTO> getMoviesByDate(LocalDate date);

    void updateMovie(Long id, MovieDTO movieDTO);

    void deleteMovie(Long id);
}
