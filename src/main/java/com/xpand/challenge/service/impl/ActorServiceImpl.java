package com.xpand.challenge.service.impl;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.Actor;
import com.xpand.challenge.model.projection.ActorProjection;
import com.xpand.challenge.repository.ActorRepository;
import com.xpand.challenge.repository.MovieRepository;
import com.xpand.challenge.service.ActorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public ActorServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public ActorDTO getActorById(Long id) {
        return actorRepository.findById(id).map(ActorDTO::convert).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActorProjection> getActorByIdActor(Long id) {
        return actorRepository.getActorByIdActor(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActorDTO> getActors() {
        return actorRepository.findAll().stream().map(ActorDTO::convert).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ActorDTO createActor(ActorDTO actorDTO) {
        var actor = Actor.convert(actorDTO);
        return ActorDTO.convert(actorRepository.save(actor));
    }

    @Transactional
    @Override
    public void updateActor(Long id, ActorDTO actorDTO) {
        actorRepository.findById(id).orElseThrow();
        var actor = Actor.convert(actorDTO);
        actor.setId(id);
        actorRepository.save(actor);
    }

    @Transactional
    @Override
    public void deleteActor(Long id) {
        boolean b = movieRepository
                .findAll().stream()
                .anyMatch(m -> m.getActors()
                        .stream()
                        .anyMatch(a -> a.getId().equals(id)));
        if (b) {
            throw new DataIntegrityViolationException("Unable to delete an anime linked to a movie");
        }
        actorRepository.deleteById(id);
    }
}
