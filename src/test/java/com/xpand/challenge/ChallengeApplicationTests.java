package com.xpand.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import com.xpand.challenge.dto.IdentifiableMovieDTO;
import com.xpand.challenge.dto.MovieDTO;

import org.junit.jupiter.api.Test;
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


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ChallengeApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void doTestGetMovies() {
		assertEquals(HttpStatus.OK.value(), restTemplate.getForEntity("http://localhost:"+port+"/movies", List.class).getStatusCodeValue());
	}

	@Test
	void doTestGetMovie() {
		ResponseEntity<IdentifiableMovieDTO> response = restTemplate.getForEntity("http://localhost:"+port+"/movies/1", IdentifiableMovieDTO.class);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
		assertEquals(1, response.getBody().getId());
	}

	@Test
	void doTestGetMovieNotFound() {
		ResponseEntity<IdentifiableMovieDTO> response = restTemplate.getForEntity("http://localhost:"+port+"/movies/125", IdentifiableMovieDTO.class);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
	}

	@Test
	void doTestGetMovieByDate() {
		ResponseEntity<List<IdentifiableMovieDTO>> response = restTemplate.exchange("http://localhost:"+port+"/movies?date=2019-09-03", HttpMethod.GET, null,
			new ParameterizedTypeReference<List<IdentifiableMovieDTO>>() {});
		assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
		assertTrue(response.getBody().size() > 0);
	}

	@Test
	void doTestGetMovieByDateEmpty() {
		ResponseEntity<List<IdentifiableMovieDTO>> response = restTemplate.exchange("http://localhost:"+port+"/movies?date=2025-09-03", HttpMethod.GET, null,
			new ParameterizedTypeReference<List<IdentifiableMovieDTO>>() {});
		assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
		assertTrue(response.getBody().size() == 0);
	}

	@Test
	void doTestUpdateMovie() {
		MovieDTO dto = new MovieDTO();
        dto.setTitle("New Title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());
		HttpEntity<MovieDTO> putRequest = new HttpEntity<>(dto);
		ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:"+port+"/movies/1", HttpMethod.PUT, putRequest, Void.class);
		assertEquals(HttpStatus.NO_CONTENT.value(), putResponse.getStatusCode().value());
		ResponseEntity<IdentifiableMovieDTO> getResponse = restTemplate.getForEntity("http://localhost:"+port+"/movies/1", IdentifiableMovieDTO.class);
		assertEquals(HttpStatus.OK.value(), getResponse.getStatusCode().value());
		assertEquals("New Title", getResponse.getBody().getTitle());
	}

	@Test
	void doTestUpdateMovieBadRequest() {
		MovieDTO dto = new MovieDTO();
        dto.setTitle("New Title");
        dto.setRank(15f);
        dto.setDate(LocalDate.now());
		HttpEntity<MovieDTO> putRequest = new HttpEntity<>(dto);
		ResponseEntity<?> putResponse = restTemplate.exchange("http://localhost:"+port+"/movies/1", HttpMethod.PUT, putRequest, Void.class);
		assertEquals(HttpStatus.BAD_REQUEST.value(), putResponse.getStatusCode().value());
	}

	@Test
	void doTestDeleteMovie() {
		ResponseEntity<?> response = restTemplate.exchange("http://localhost:"+port+"/movies/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
		assertEquals(HttpStatus.NOT_FOUND.value(), restTemplate.getForEntity("http://localhost:"+port+"/movies/1", IdentifiableMovieDTO.class).getStatusCode().value());
	}
}
