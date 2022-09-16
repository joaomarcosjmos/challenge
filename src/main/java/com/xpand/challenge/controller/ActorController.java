package com.xpand.challenge.controller;

import com.xpand.challenge.dto.ActorDTO;
import com.xpand.challenge.model.projection.ActorProjection;
import com.xpand.challenge.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/actors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> getActors() {
        return ResponseEntity.ok().body(actorService.getActors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        return ResponseEntity.ok().body(actorService.getActorById(id));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<List<ActorProjection>> getActorByIdMovie(@PathVariable Long id) {
        return ResponseEntity.ok().body(actorService.getActorByIdActor(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActorDTO> createActor(@RequestBody ActorDTO actorDTO) {
        return new ResponseEntity<>(actorService.createActor(actorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Long id, @RequestBody ActorDTO actorDTO) {
        actorService.updateActor(id, actorDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActorDTO> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}


