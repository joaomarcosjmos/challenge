package com.xpand.challenge.controller;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.projection.ActorProjection;
import com.xpand.challenge.service.impl.ActorServiceImpl;
import com.xpand.challenge.util.CreateActor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class ActorControllerTest {

    @InjectMocks
    private ActorController controller;

    @Mock
    private ActorServiceImpl serviceMock;

    @Mock
    private ActorProjection actorProjection;

    @BeforeEach
    void setUp() {
        BDDMockito.when(serviceMock.getActors())
                .thenReturn(List.of(CreateActor.createValidActorDTO()));


        BDDMockito.when(serviceMock.getActorById(ArgumentMatchers.anyLong()))
                .thenReturn(CreateActor.createValidActorDTO());

        BDDMockito.when(serviceMock.createActor(ArgumentMatchers.any(ActorDTO.class)))
                .thenReturn(CreateActor.createValidActorDTO());

        BDDMockito.doNothing().when(serviceMock).updateActor(ArgumentMatchers.anyLong(), ArgumentMatchers.any(ActorDTO.class));

        BDDMockito.doNothing().when(serviceMock).deleteActor(ArgumentMatchers.anyLong());

    }

    @Test
    void getActors_WheSucessful() {
        String expectedName = CreateActor.createValidActorDTO().getName();
        List < ActorDTO> actorDto = controller.getActors().getBody();

        Assertions.assertThat(actorDto).isNotNull();

        Assertions.assertThat(actorDto)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(actorDto.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void getActorById_WheSucessful() {
        String expectedName = CreateActor.createValidActorDTO().getName();
        var actorDTO = controller.getActorById(1L).getBody();
        Assertions.assertThat(actorDTO).isNotNull();

        Assertions.assertThat(actorDTO.getName()).isEqualTo(expectedName);
    }

    @Test
    void getActorById_WheActorNotFound() {
        BDDMockito.when(serviceMock.getActorById(ArgumentMatchers.any()))
                .thenReturn(ArgumentMatchers.isNull());

        var actorDTO = controller.getActorById(125L).getBody();

        Assertions.assertThat(actorDTO)
                .isNull();
    }

    @Test
    void createActor_WheSucessful() {
        String expectedName = CreateActor.createValidActorDTO().getName();

        var actor = controller.createActor(CreateActor.createValidActorDTO()).getBody();

        Assertions.assertThat(actor.getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    void updateActor_WheSucessful() {
        var actor = controller.updateActor(1L, CreateActor.createValidActorDTO());

        Assertions.assertThat(actor.getStatusCodeValue()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteActor_WheActorNotFound() {
        var actor = controller.deleteActor(1L);

        Assertions.assertThat(actor.getStatusCodeValue()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}