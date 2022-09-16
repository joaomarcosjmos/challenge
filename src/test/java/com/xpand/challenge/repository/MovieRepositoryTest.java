package com.xpand.challenge.repository;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;

    @Test
    void test(){
    }

    @Test
    void findById_ReturnMovie_WhenSuccessful() {
        var movieToBeSaved = createMovie();

        var movieSaved = repository.save(Movie.convert(movieToBeSaved));

        var id = movieSaved.getId();

        var movie = this.repository.findById(id).get();

        Assertions.assertThat(movie).isNotNull();

        Assertions.assertThat(movieSaved.getTitle()).isEqualTo(movieSaved.getTitle());
    }

    @Test
    void save_PersistMovie_WhenSuccessful() {
        var movieToBeSaved = createMovie();

        var movieSaved = repository.save(Movie.convert(movieToBeSaved));

        Assertions.assertThat(movieSaved).isNotNull();

        Assertions.assertThat(movieSaved.getId()).isNotNull();

        Assertions.assertThat(movieSaved.getTitle()).isEqualTo(movieToBeSaved.getTitle());
    }

    @Test
    void save_UpdateMovie_WhenSuccessful() {
        var movieToBeSaved = createMovie();

        var movieSaved = repository.save(Movie.convert(movieToBeSaved));

        movieSaved.setTitle("New Title");

        var movieUpdate = repository.save(movieSaved);

        Assertions.assertThat(movieUpdate).isNotNull();

        Assertions.assertThat(movieUpdate.getId()).isNotNull();

        Assertions.assertThat(movieUpdate.getTitle()).isEqualTo(movieSaved.getTitle());
    }

    @Test
    void delete_RemoveMovie_WhenSuccessful() {
        var movieToBeSaved = createMovie();

        var movieSaved = repository.save(Movie.convert(movieToBeSaved));

        this.repository.delete(movieSaved);

        Optional<Movie> movieOptional = this.repository.findById(movieSaved.getId());

        Assertions.assertThat(movieOptional).isEmpty();

    }

    private MovieDTO createMovie() {
        var movie = new MovieDTO();
        movie.setTitle("Old Title");
        movie.setRank(5f);
        movie.setDate(LocalDate.now());
        movie.setRevenue(new BigDecimal(100000));
        return movie;
    }

}