package com.xpand.challenge;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.validator.Validator;
import com.xpand.challenge.validator.impl.MovieValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MovieValidatorTests {

    static Validator<MovieDTO> validator;

    @BeforeAll
    static void setup() {
        validator = new MovieValidator();
    }

    @Test
    void doTestValidMovie() {
        MovieDTO dto = new MovieDTO();
        dto.setTitle("title");
        dto.setRank(10f);
        dto.setDate(LocalDate.now());
        assertDoesNotThrow(() -> validator.validate(dto));
        dto.setRevenue(new BigDecimal(100000));
        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    void doTestInvalidTitle() {
        MovieDTO dto = new MovieDTO();
        assertThrowsExactly(IllegalArgumentException.class, () -> validator.validate(dto));
    }

    @Test
    void doTestInvalidRank() {
        MovieDTO dto = new MovieDTO();
        dto.setTitle("title");
        dto.setRank(15f);
        assertThrowsExactly(IllegalArgumentException.class, () -> validator.validate(dto));
        dto.setRank(null);
        assertThrowsExactly(IllegalArgumentException.class, () -> validator.validate(dto));
    }

    @Test
    void doTestInvalidDate() {
        MovieDTO dto = new MovieDTO();
        dto.setTitle("title");
        dto.setRank(5f);
        assertThrowsExactly(IllegalArgumentException.class, () -> validator.validate(dto));
    }

    @Test
    void doTestInvalidRevenue() {
        MovieDTO dto = new MovieDTO();
        dto.setTitle("title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());
        dto.setRevenue(new BigDecimal(-1));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(dto));
    }
}
