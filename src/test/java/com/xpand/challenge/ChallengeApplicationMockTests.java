package com.xpand.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpand.challenge.dto.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChallengeApplicationMockTests {

    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static MovieDTO dto;

//    private static List<Actor> actors = Arrays.asList(new Actor(1L, "Test"),
//            new Actor(2L, "Test 2"));

    @BeforeEach
    public void init(WebApplicationContext context) {
        dto = new MovieDTO();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void doTestGetMovies() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk());
    }

    @Test
    void doTestGetMovieById() throws Exception {
        mockMvc.perform(get("/movies/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void doTestGetMovieNotFound() throws Exception {
        mockMvc.perform(get("/movies/125")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void doTestGetMovieByDate() throws Exception {
        mockMvc.perform(get("/movies/date").queryParam("date", "2019-09-03")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0]", hasSize(1)));
    }

    @Test
    void doTestGetMovieByDateEmpty() throws Exception {
        mockMvc.perform(get("/movies/").queryParam("date", "2025-09-03")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    void doTestUpdateMovie() throws Exception {
        dto.setTitle("New Title");
        dto.setRank(5f);
        dto.setDate(LocalDate.now());

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/movies/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Title")));

    }

    @Test
    void doTestUpdateMovieBadRequest() throws Exception {
        dto.setTitle("New Title");
        dto.setRank(15f);
        dto.setDate(LocalDate.now());

        mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void doTestDeleteMovie() throws Exception {
        mockMvc.perform(delete("/movies/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/movies/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
