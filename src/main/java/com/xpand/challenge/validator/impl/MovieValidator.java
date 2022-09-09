package com.xpand.challenge.validator.impl;

import java.math.BigDecimal;
import java.util.Optional;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.validator.Validator;

public class MovieValidator implements Validator<MovieDTO> {

    @Override
    public void validate(MovieDTO movie) {
        Optional.ofNullable(movie.getTitle())
            .map(String::trim)
            .filter(title -> !title.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("Title must not be empty"));

        Optional.ofNullable(movie.getRank())
            .filter(rank -> rank >= 0d && rank <= 10d)
            .orElseThrow(() -> new IllegalArgumentException("Rank must be between 0 and 10"));

        Optional.ofNullable(movie.getDate())
            .orElseThrow(() -> new IllegalArgumentException("Date must not be empty"));

        Optional.ofNullable(movie.getRevenue()).ifPresent(
            revenue -> {
                if (revenue.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Revenue must be greater than 0");
            }
        );
    }

}
