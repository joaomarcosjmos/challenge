package com.xpand.challenge.service;

import com.xpand.challenge.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {

    MovieDTO getMovieById(Long id);

    @Transactional(readOnly = true)
    Page<MovieDTO> getMovies(PageRequest pageRequest);

    List<MovieDTO> getMoviesByDate(LocalDate date);

    MovieDTO createMovie(MovieDTO movieDTO);

    void updateMovie(Long id, MovieDTO movieDTO);

    void deleteMovie(Long id);
}
