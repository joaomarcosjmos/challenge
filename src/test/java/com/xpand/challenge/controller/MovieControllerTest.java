package com.xpand.challenge.controller;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.model.Movie;
import com.xpand.challenge.service.impl.MovieServiceImpl;
import com.xpand.challenge.util.CreateMovie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class MovieControllerTest {

    @InjectMocks
    private MovieController controller;

    @Mock
    private MovieServiceImpl serviceMock;

    @BeforeEach
    void setUp() {
        PageImpl<MovieDTO> moviePage = new PageImpl<>(List.of(CreateMovie.createValidMovieDTO()));
        BDDMockito.when(serviceMock.getMovies(ArgumentMatchers.any()))
                .thenReturn(moviePage);

        BDDMockito.when(serviceMock.getMovieById(ArgumentMatchers.anyLong()))
                .thenReturn(CreateMovie.createValidMovieDTO());

        BDDMockito.when(serviceMock.getMoviesByDate(ArgumentMatchers.any()))
                .thenReturn((List.of(CreateMovie.createValidMovieDTO())));

        BDDMockito.when(serviceMock.createMovie(ArgumentMatchers.any(MovieDTO.class)))
                .thenReturn(CreateMovie.createValidMovieDTO());

        BDDMockito.doNothing().when(serviceMock).updateMovie(ArgumentMatchers.anyLong(), ArgumentMatchers.any(MovieDTO.class));

        BDDMockito.doNothing().when(serviceMock).deleteMovie(ArgumentMatchers.anyLong());
    }

    @Test
    void getMovies_With_Paginatio() {
        String expectedTitle = CreateMovie.createValidMovieDTO().getTitle();
        Page<MovieDTO> dtoPage = controller.getMovies(1, 3).getBody();

        Assertions.assertThat(dtoPage).isNotNull();

        Assertions.assertThat(dtoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(dtoPage.toList().get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    void getMovieById_WheSucessful() {
        Long expectedId = CreateMovie.createValidMovieDTO().getId();
        var movie = controller.getMovieById(1L).getBody();
        Assertions.assertThat(movie).isNotNull();

        Assertions.assertThat(movie.getId()).isEqualTo(expectedId);
    }

    @Test
    void getMoviesByDate_WheMovieNotFound() {
        BDDMockito.when(serviceMock.getMoviesByDate(ArgumentMatchers.any()))
                .thenReturn(Collections.emptyList());

        var movie = controller.getMoviesByDate(LocalDate.now()).getBody();

        Assertions.assertThat(movie)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void createMovie_WheSucessful() {
        String expectedTitle = CreateMovie.createValidMovieDTO().getTitle();

        var movie = controller.createMovie(CreateMovie.createValidMovieDTO()).getBody();

        Assertions.assertThat(movie.getTitle()).isNotNull().isEqualTo(expectedTitle);
    }

    @Test
    void updateMovie_WheSucessful() {
        var movie = controller.updateMovie(1L, CreateMovie.createValidMovieDTO());

        Assertions.assertThat(movie.getStatusCodeValue()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteMovie_WheSucessful() {
        var movie = controller.deleteMovie(1L);

        Assertions.assertThat(movie.getStatusCodeValue()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}