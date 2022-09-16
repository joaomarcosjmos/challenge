package com.xpand.challenge.repository;

import com.xpand.challenge.model.Actor;
import com.xpand.challenge.model.projection.ActorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query(value = "SELECT a.id, a.name " +
            "FROM actor a " +
            "JOIN movie_actor ma ON a.id = ma.actor_id " +
            "JOIN movie m ON m.id = ma.movie_id AND m.id = :id", nativeQuery = true)
    List<ActorProjection> getActorByIdActor(Long id);
}
