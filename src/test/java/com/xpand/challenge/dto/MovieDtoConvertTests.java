package com.xpand.challenge.dto;

import com.xpand.challenge.model.Actor;
import com.xpand.challenge.model.Movie;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MovieDtoConvertTests {


    @Test
    void doTestfromMovieDTO() {
        var dto = new MovieDTO();
        dto.setTitle("title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());
        dto.setRevenue(new BigDecimal(100000));
        Movie movie = Movie.convert(dto);
        assertNotNull(movie);
        assertEquals(dto.getTitle(), movie.getTitle());
        assertEquals(dto.getRank(), movie.getRank());
        assertEquals(dto.getDate(), movie.getDate());
        assertEquals(dto.getRevenue(), movie.getRevenue());
    }

    @Test
    void doTesttoMovieDTO() {
        var movie = new Movie();
        movie.setId(1l);
        movie.setTitle("title");
        movie.setRank(5f);
        movie.setDate(LocalDate.now());
        movie.setRevenue(new BigDecimal(100000));
        movie.setActors(Arrays.asList(new Actor(1L, "Jack Nicholson")));
        var dto = MovieDTO.convert(movie);
        assertNotNull(dto);
        assertEquals(movie.getId(), dto.getId());
        assertEquals(movie.getTitle(), dto.getTitle());
        assertEquals(movie.getRank(), dto.getRank());
        assertEquals(movie.getDate(), dto.getDate());
        assertEquals(movie.getRevenue(), dto.getRevenue());
        assertNotEquals(movie.getActors(), dto.getActors());
    }

    @Test
    void doTestNull() {
        assertNull(Movie.convert(null));
        assertNull(MovieDTO.convert(null));
    }

}
