package com.xpand.challenge.dto;

import com.xpand.challenge.model.Movie;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieDTO {

    private Long id;
    private String title;
    private LocalDate date;
    private Float rank;
    private BigDecimal revenue;
    private List<ActorDTO> actors;

    public MovieDTO() {
        actors = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
    }

    public boolean hasRankValid() {
        return Objects.nonNull(rank) && (rank >= 0d && rank <= 10d);
    }

    public boolean hasTitleValid() {
        return !ObjectUtils.isEmpty(title) && !ObjectUtils.isEmpty(title.trim());
    }

    public boolean hasDateValid() {
        return Objects.nonNull(date);
    }

    public boolean hasRevenue() {
        return Objects.nonNull(revenue);
    }

    public boolean hasRevenueValid() {
        return hasRevenue() && (revenue.compareTo(BigDecimal.ZERO) > 0);
    }

    public static MovieDTO convert(Movie movie) {
        return Optional.ofNullable(movie).map(m -> {
            MovieDTO dto = new MovieDTO();
            dto.setId(movie.getId());
            dto.setDate(movie.getDate());
            dto.setTitle(movie.getTitle());
            dto.setRank(movie.getRank());
            dto.setRevenue(movie.getRevenue());
            dto.setActors(movie.getActors().stream().map(x -> new ActorDTO(x)).collect(Collectors.toList()));
            return dto;
        }).orElse(null);
    }
}
