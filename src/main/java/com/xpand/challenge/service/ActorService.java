package com.xpand.challenge.service;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.projection.ActorProjection;

import java.util.List;

public interface ActorService {
    
    ActorDTO createActor(ActorDTO actorDTO);

    ActorDTO getActorById(Long id);

    List<ActorProjection> getActorByIdActor(Long id);

    List<ActorDTO> getActors();

    void updateActor(Long id, ActorDTO actorDTO);

    void deleteActor(Long id);
}
