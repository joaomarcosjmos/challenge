package com.xpand.challenge.dto;

import java.util.Optional;

import com.xpand.challenge.model.Movie;

public class MovieDTOMapper {

    public static IdentifiableMovieDTO toMovieDTO(Movie movie) {
        return Optional.ofNullable(movie).map(m -> {
            IdentifiableMovieDTO dto = new IdentifiableMovieDTO();
            dto.setId(movie.getId());
            dto.setDate(movie.getDate());
            dto.setTitle(movie.getTitle());
            dto.setRank(movie.getRank());
            dto.setRevenue(movie.getRevenue());
            return dto;
        }).orElse(null);
    }

    public static Movie fromMovieDTO(MovieDTO dto) {
        return Optional.ofNullable(dto).map(d -> {
            Movie movie = new Movie();
            movie.setDate(dto.getDate());
            movie.setTitle(dto.getTitle());
            movie.setRank(dto.getRank());
            movie.setRevenue(dto.getRevenue());
            return movie;
        }).orElse(null);
    }

}
