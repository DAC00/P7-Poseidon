package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.Rating;
import com.opcr.poseidon.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @Test
    public void validate() throws Exception {
        doNothing().when(ratingService).saveRating(any(Rating.class));
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "99"))
                .andExpect(redirectedUrl("/rating/list"));
        verify(ratingService).saveRating(any(Rating.class));
    }

    @Test
    public void whenMoodysRatingIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "99"))
                .andExpect(content().string(containsString("MoodysRating is mandatory.")));
        verify(ratingService, never()).saveRating(any(Rating.class));
    }

    @Test
    public void whenSandPRatingIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", "moodysRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "99"))
                .andExpect(content().string(containsString("SandPRating is mandatory.")));
        verify(ratingService, never()).saveRating(any(Rating.class));
    }

    @Test
    public void whenFitchRatingIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("orderNumber", "99"))
                .andExpect(content().string(containsString("FitchRating is mandatory.")));
        verify(ratingService, never()).saveRating(any(Rating.class));
    }

    @Test
    public void whenOrderNumberIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating"))
                .andExpect(content().string(containsString("Order is mandatory.")));
        verify(ratingService, never()).saveRating(any(Rating.class));
    }

    @Test
    public void whenOrderNumberIsNegativeShowError() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "-99"))
                .andExpect(content().string(containsString("Order must be positive.")));
        verify(ratingService, never()).saveRating(any(Rating.class));
    }
}
