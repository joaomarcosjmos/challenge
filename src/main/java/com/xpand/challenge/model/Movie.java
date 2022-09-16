package com.xpand.challenge.model;

import com.xpand.challenge.dto.MovieDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "rank", nullable = false)
    private Float rank;

    @Column(name = "revenue")
    private BigDecimal revenue;

   @ManyToMany
   @JoinTable(name = "movie_actor",
   joinColumns = @JoinColumn(name = "movie_id"),
   inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors = new ArrayList<>();

    public Movie() {
    }

    public Movie(Long id, String title, LocalDate date, Float rank, BigDecimal revenue) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rank = rank;
        this.revenue = revenue;
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

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public static Movie convert(MovieDTO dto) {
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
