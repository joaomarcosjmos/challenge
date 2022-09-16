package com.xpand.challenge.dto;

import com.xpand.challenge.model.Actor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActorDtoConvertTests {


    @Test
    void doTestfromActorDTO() {
        var dto = new ActorDTO(null, "Jack Nicholson");
        var actor = Actor.convert(dto);
        assertNotNull(actor);
        assertEquals(dto.getName(), actor.getName());
    }

    @Test
    void doTestToActorDTO() {
        var actor = new Actor();
        actor.setId(1l);
        actor.setName("Jack Nicholson");
        var dto = ActorDTO.convert(actor);
        assertNotNull(dto);
        assertEquals(actor.getId(), dto.getId());
        assertEquals(actor.getName(), dto.getName());
    }

    @Test
    void doTestConstructorActorDTO() {
        var actor = new Actor();
        actor.setId(1l);
        actor.setName("Jack Nicholson");
        var dto = new ActorDTO(actor);
        assertNotNull(dto);
        assertEquals(actor.getId(), dto.getId());
        assertEquals(actor.getName(), dto.getName());
    }

    @Test
    void doTestNull() {
        assertNull(Actor.convert(null));
        assertNull(ActorDTO.convert(null));
    }

}
