package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.CurvePoint;
import com.opcr.poseidon.services.CurvePointService;
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

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    @Test
    public void validate() throws Exception {
        doNothing().when(curvePointService).saveCurvePoint(any(CurvePoint.class));
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("term", "22")
                        .param("value", "99"))
                .andExpect(redirectedUrl("/curvePoint/list"));
        verify(curvePointService).saveCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void whenTermIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("value", "99"))
                .andExpect(content().string(containsString("Term must be at least 0.01.")));
        verify(curvePointService, never()).saveCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void whenTermIsNegativeShowError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("value", "99"))
                .andExpect(content().string(containsString("Term must be at least 0.01.")));
        verify(curvePointService, never()).saveCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void whenValueIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("term", "22"))
                .andExpect(content().string(containsString("Value must be at least 0.01.")));
        verify(curvePointService, never()).saveCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void whenValueIsNegativeShowError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("term", "22")
                        .param("value", "-99"))
                .andExpect(content().string(containsString("Value must be at least 0.01.")));
        verify(curvePointService, never()).saveCurvePoint(any(CurvePoint.class));
    }
}