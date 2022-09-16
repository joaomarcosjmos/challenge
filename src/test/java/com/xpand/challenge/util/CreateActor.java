package com.xpand.challenge.util;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.Actor;

public class CreateActor {

    public static ActorDTO createValidActorDTO() {
        var dto = new ActorDTO(null, "Jack Nicholson");
        return dto;
    }

    public static Actor createActorToBeSaved() {
        var actor = new Actor();
        actor.setId(1l);
        actor.setName("Jack Nicholson");
        return null;
    }
}
