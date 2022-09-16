package com.xpand.challenge.service.impl;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.Actor;
import com.xpand.challenge.model.projection.ActorProjection;
import com.xpand.challenge.repository.ActorRepository;
import com.xpand.challenge.service.ActorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public ActorDTO getActorById(Long id) {
        return actorRepository.findById(id).map(ActorDTO::convert).orElseThrow();
    }

    @Override
    public List<ActorProjection> getActorByIdMovie(Long id) {
        return actorRepository.getActorByIdMovie(id);
    }

    @Override
    public List<ActorDTO> getActors() {
        return actorRepository.findAll().stream().map(ActorDTO::convert).collect(Collectors.toList());
    }

    @Override
    public ActorDTO createActor(ActorDTO actorDTO) {
        var movie = Actor.convert(actorDTO);
        return ActorDTO.convert(actorRepository.save(movie));
    }

    @Override
    public void updateActor(Long id, ActorDTO actorDTO) {
        actorRepository.findById(id).orElseThrow();
        var actor = Actor.convert(actorDTO);
        actor.setId(id);
        actorRepository.save(actor);
    }

    @Override
    public void deleteActor(Long id) {
        try {
            actorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Invalid operation, actor linked to a movie");
        }

    }
}
