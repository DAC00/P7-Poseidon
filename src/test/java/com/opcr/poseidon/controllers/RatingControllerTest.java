package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.Rating;
import com.opcr.poseidon.services.RatingService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingService ratingService;

    private Rating ratingTest;

    @BeforeEach
    public void setUp() {
        this.ratingTest = new Rating();
        ratingTest.setMoodysRating("MoodysRating");
        ratingTest.setSandPRating("SandPRating");
        ratingTest.setFitchRating("FitchRating");
        ratingTest.setOrderNumber(10);
    }

    @Test
    public void saveRatingTest() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", ratingTest.getMoodysRating())
                        .param("sandPRating", ratingTest.getSandPRating())
                        .param("fitchRating", ratingTest.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingTest.getOrderNumber())))
                .andExpect(redirectedUrl("/rating/list"));

        List<Rating> list = ratingService.getRatings();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                rList -> rList.getMoodysRating().equals(ratingTest.getMoodysRating())
                        && rList.getSandPRating().equals(ratingTest.getSandPRating())
                        && rList.getFitchRating().equals(ratingTest.getFitchRating())
                        && rList.getOrderNumber().equals(ratingTest.getOrderNumber())
        ));
    }

    @Test
    public void updateRatingTest() throws Exception {
        ratingService.saveRating(ratingTest);
        String updatedFitchRating = "updatedFitchRating";
        int updatedOrderNumber = 20;

        mockMvc.perform(post("/rating/update/" + ratingTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", ratingTest.getMoodysRating())
                        .param("sandPRating", ratingTest.getSandPRating())
                        .param("fitchRating", updatedFitchRating)
                        .param("orderNumber", String.valueOf(updatedOrderNumber)))
                .andExpect(redirectedUrl("/rating/list"));

        List<Rating> list = ratingService.getRatings();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                rList -> rList.getMoodysRating().equals(ratingTest.getMoodysRating())
                        && rList.getSandPRating().equals(ratingTest.getSandPRating())
                        && rList.getFitchRating().equals(updatedFitchRating)
                        && rList.getOrderNumber().equals(updatedOrderNumber)
        ));
    }

    @Test
    public void deleteRatingTest() throws Exception {
        ratingService.saveRating(ratingTest);

        mockMvc.perform(get("/rating/delete/" + ratingTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(redirectedUrl("/rating/list"));

        assertEquals(0, ratingService.getRatings().size());
    }
}
