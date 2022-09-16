package com.xpand.challenge.integration;

import com.xpand.challenge.dto.MovieDTO;
import com.xpand.challenge.model.Actor;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MovieIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static MovieDTO dto;

    private static List<Actor> actors = Arrays.asList(new Actor(1L, "Test"), new Actor(2L, "Test 2"));

    @BeforeEach
    void setUp() {
        dto = new MovieDTO();
    }

    @Test
    void doTestGetMovies() {
        assertEquals(HttpStatus.OK.value(), restTemplate.exchange("http://localhost:" + port + "/movies", HttpMethod.GET, null,
                new ParameterizedTypeReference<MovieDTO>() {
                }).getStatusCodeValue());
    }

    @Test
    void doTestGetMovieById() {
        ResponseEntity<MovieDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/movies/1", MovieDTO.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void doTestGetMovieNotFound() {
        ResponseEntity<MovieDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/movies/125", MovieDTO.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }

    @Test
    void doTestGetMovieByDate() {
        ResponseEntity<List<MovieDTO>> response = restTemplate.exchange("http://localhost:" + port + "/movies/date?date=2019-09-03", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void doTestGetMovieByDateEmpty() {
        ResponseEntity<List<MovieDTO>> response = restTemplate.exchange("http://localhost:" + port + "/movies/date?date=2025-09-03", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertTrue(response.getBody().size() == 0);
    }

    @Test
    void doTestCreateMovie() {
        dto.setTitle("New Title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());
        HttpEntity<MovieDTO> postRequest = new HttpEntity<>(dto);
        ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:" + port + "/movies", HttpMethod.POST, postRequest, Void.class);
        assertEquals(HttpStatus.CREATED.value(), putResponse.getStatusCode().value());
        ResponseEntity<MovieDTO> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/movies/1", MovieDTO.class);
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
        assertEquals(getResponse.getBody().getId(), getResponse.getBody().getId());
    }

    @Test
    void doTestUpdateMovie() {
        dto.setTitle("New Title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());
        HttpEntity<MovieDTO> putRequest = new HttpEntity<>(dto);
        ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:" + port + "/movies/1", HttpMethod.PUT, putRequest, Void.class);
        assertEquals(HttpStatus.NO_CONTENT.value(), putResponse.getStatusCode().value());
        ResponseEntity<MovieDTO> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/movies/1", MovieDTO.class);
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
        assertEquals("New Title", getResponse.getBody().getTitle());
    }

    @Test
    void doTestUpdateMovieBadRequest() {
        dto.setTitle("New Title");
        dto.setRank(15f);
        dto.setDate(LocalDate.now());
        HttpEntity<MovieDTO> putRequest = new HttpEntity<>(dto);
        ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:" + port + "/movies/1", HttpMethod.PUT, putRequest, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), putResponse.getStatusCode().value());
    }

    @Test
    void doTestDeleteMovie() {
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:" + port + "/movies/2", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
        assertEquals(HttpStatus.NOT_FOUND.value(), restTemplate.getForEntity("http://localhost:" + port + "/movies/2", MovieDTO.class).getStatusCode().value());
    }
}
