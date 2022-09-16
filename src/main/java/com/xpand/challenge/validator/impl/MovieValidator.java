package com.xpand.challenge.validator.impl;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.validator.Validator;

public class MovieValidator implements Validator<MovieDTO> {

    @Override
    public void validate(MovieDTO movie) {
        if (!movie.hasTitleValid()) {
            throw new IllegalArgumentException("Title must not be empty");
        }

        if (!movie.hasRankValid()) {
            throw new IllegalArgumentException("Rank must be between 0 and 10");
        }

        if (!movie.hasDateValid()) {
            throw new IllegalArgumentException("Date must not be empty");
        }

        if (movie.hasRevenue() && !movie.hasRevenueValid()) {
            throw new IllegalArgumentException("Revenue must be greater than 0");
        }
    }

}
