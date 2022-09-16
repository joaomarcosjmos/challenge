package com.xpand.challenge.integration;

import com.xpand.challenge.dto.ActorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ActorIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static ActorDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ActorDTO();
    }

    @Test
    void doTestGetActors() {
        assertEquals(HttpStatus.OK.value(), restTemplate.exchange("http://localhost:" + port + "/actors", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ActorDTO>>() {
                }).getStatusCodeValue());
    }

    @Test
    void doTestGetActorById() {
        ResponseEntity<ActorDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/actors/1", ActorDTO.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void doTestGetActorNotFound() {
        ResponseEntity<ActorDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/actors/125", ActorDTO.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }

    @Test
    void doTestCreateActor() {
        dto.setName("Robert De Niro");
        HttpEntity<ActorDTO> postRequest = new HttpEntity<>(dto);
        ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:" + port + "/actors", HttpMethod.POST, postRequest, Void.class);
        assertEquals(HttpStatus.CREATED.value(), putResponse.getStatusCode().value());
        ResponseEntity<ActorDTO> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/movies/1", ActorDTO.class);
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
        assertEquals(getResponse.getBody().getId(), getResponse.getBody().getId());
    }

    @Test
    void doTestUpdateActor() {
        dto.setName("Jack Nicholson");
        HttpEntity<ActorDTO> putRequest = new HttpEntity<>(dto);
        ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:" + port + "/actors/1", HttpMethod.PUT, putRequest, Void.class);
        assertEquals(HttpStatus.NO_CONTENT.value(), putResponse.getStatusCode().value());
        ResponseEntity<ActorDTO> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/actors/1", ActorDTO.class);
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
        assertEquals(putRequest.getBody().getName(), getResponse.getBody().getName());
    }

    @Test
    void doTestDeleteActor() {
        ResponseEntity<ActorDTO> response = restTemplate.exchange("http://localhost:" + port + "/actors/4", HttpMethod.DELETE, null, ActorDTO.class);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
        assertEquals(HttpStatus.NOT_FOUND.value(), restTemplate.getForEntity("http://localhost:" + port + "/actors/4", ActorDTO.class).getStatusCode().value());
    }
}
