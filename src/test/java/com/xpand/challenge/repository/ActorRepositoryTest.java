package com.xpand.challenge.repository;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.Actor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class ActorRepositoryTest {

    @Autowired
    private ActorRepository repository;

    @Test
    void test() {
    }

    @Test
    void findById_ReturnActor_WhenSuccessful() {
        var actorSaved = createActor();

        var id = actorSaved.getId();

        var actor = this.repository.findById(id).get();

        Assertions.assertThat(actor).isNotNull();

        Assertions.assertThat(actorSaved.getName()).isEqualTo(actorSaved.getName());
    }

    @Test
    void save_PersistActor_WhenSuccessful() {
        var actorSaved = createActor();

        Assertions.assertThat(actorSaved).isNotNull();

        Assertions.assertThat(actorSaved.getId()).isNotNull();

        Assertions.assertThat(actorSaved.getName()).isEqualTo(actorSaved.getName());
    }

    @Test
    void save_UpdateActor_WhenSuccessful() {
        var actorSaved = createActor();

        actorSaved.setName("New Title");

        var actorUpdate = repository.save(actorSaved);

        Assertions.assertThat(actorUpdate).isNotNull();

        Assertions.assertThat(actorUpdate.getId()).isNotNull();

        Assertions.assertThat(actorUpdate.getName()).isEqualTo(actorSaved.getName());
    }

    @Test
    void delete_RemoveActor_WhenSuccessful() {
        var actorSaved = createActor();

        this.repository.delete(actorSaved);

        Optional<Actor> actorOptional = this.repository.findById(actorSaved.getId());

        Assertions.assertThat(actorOptional).isEmpty();

    }

    private Actor createActor() {
        var dto = new ActorDTO();
        dto.setName("Old Title");
        var actorSaved = repository.save(Actor.convert(dto));
        return actorSaved;
    }


}