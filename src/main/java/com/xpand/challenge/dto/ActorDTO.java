package com.xpand.challenge.dto;

import com.xpand.challenge.model.Actor;

import java.util.Optional;

public class ActorDTO {

    private Long id;
    private String name;

    public ActorDTO() {
    }

    public ActorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ActorDTO convert(Actor actor) {
        return Optional.ofNullable(actor).map(d -> {
            ActorDTO dto = new ActorDTO();
            dto.setId(actor.getId());
            dto.setName(actor.getName());
            return dto;
        }).orElse(null);
    }
}
