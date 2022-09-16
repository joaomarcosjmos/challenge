package com.xpand.challenge.service.impl;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.model.Actor;
import com.xpand.challenge.model.Movie;
import com.xpand.challenge.repository.MovieRepository;
import com.xpand.challenge.service.MovieService;
import com.xpand.challenge.validator.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final Validator<MovieDTO> movieValidator;

    public MovieServiceImpl(MovieRepository movieRepository, Validator<MovieDTO> movieValidator) {
        this.movieRepository = movieRepository;
        this.movieValidator = movieValidator;
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        return movieRepository.findById(id).map(MovieDTO::convert).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MovieDTO> getMovies(PageRequest pageRequest) {
        Page<Movie> page = movieRepository.findAll(pageRequest);
        movieRepository.findMoviesActors(page.stream().collect(Collectors.toList()));
        return page.map(MovieDTO::convert);
    }

    public List<MovieDTO> getMoviesByDate(LocalDate date) {
        return movieRepository.findMoviesByDate(date).stream()
                .map(MovieDTO::convert)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        movieValidator.validate(movieDTO);
        var movie = Movie.convert(movieDTO);
        movie.setActors(movieDTO.getActors().stream().map(x -> new Actor(x)).collect(Collectors.toList()));
        return MovieDTO.convert(movieRepository.save(movie));
    }

    @Override
    public void updateMovie(Long id, MovieDTO movieDTO) {
        movieRepository.findById(id).orElseThrow();
        movieValidator.validate(movieDTO);
        var movie = Movie.convert(movieDTO);
        movie.setId(id);
        movie.setActors(movieDTO.getActors().stream().map(x -> new Actor(x)).collect(Collectors.toList()));
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        var movie = movieRepository.findById(id).orElseThrow();
        movieRepository.deleteById(movie.getId());
    }
}
