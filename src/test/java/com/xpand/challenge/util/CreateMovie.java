package com.xpand.challenge.util;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.model.Actor;
import com.xpand.challenge.model.Movie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class CreateMovie {

    public static MovieDTO createValidMovieDTO() {
        var dto = new MovieDTO();
        dto.setTitle("title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());
        dto.setRevenue(new BigDecimal(100000));
        return dto;
    }

    public static Movie createMovieToBeSaved() {
        var movie = new Movie();
        movie.setTitle("title");
        movie.setRank(5f);
        movie.setDate(LocalDate.now());
        movie.setRevenue(new BigDecimal(100000));
        movie.setActors(Arrays.asList(new Actor(1L, "Jack Nicholson")));
        return movie;
    }

    public static Movie createValidMovie() {
        var movie = new Movie();
        movie.setId(1l);
        movie.setTitle("title");
        movie.setRank(5f);
        movie.setDate(LocalDate.now());
        movie.setRevenue(new BigDecimal(100000));
        movie.setActors(Arrays.asList(new Actor(1L, "Jack Nicholson")));
        return movie;
    }

    public static Movie createValidUpdateMovie() {
        var movie = new Movie();
        movie.setId(1l);
        movie.setTitle("New title");
        movie.setRank(5f);
        movie.setDate(LocalDate.now());
        movie.setRevenue(new BigDecimal(100000));
        movie.setActors(Arrays.asList(new Actor(1L, "Jack Nicholson")));
        return movie;
    }
}
