package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.CurvePoint;
import com.opcr.poseidon.services.CurvePointService;
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
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointService curvePointService;

    private CurvePoint curvePointTest;


    @BeforeEach
    public void setUp() {
        this.curvePointTest = new CurvePoint();
        curvePointTest.setTerm(10);
        curvePointTest.setValue(100);
    }

    @Test
    public void saveCurvePointTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("term", String.valueOf(curvePointTest.getTerm()))
                        .param("value", String.valueOf(curvePointTest.getValue())))
                .andExpect(redirectedUrl("/curvePoint/list"));

        List<CurvePoint> list = curvePointService.getCurvePoints();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                cList -> cList.getTerm() == curvePointTest.getTerm()
                        && cList.getValue() == curvePointTest.getValue()
        ));
    }

    @Test
    public void updateCurvePointTest() throws Exception {
        curvePointService.saveCurvePoint(curvePointTest);
        double updatedTerm = 99;

        mockMvc.perform(post("/curvePoint/update/" + curvePointTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("term", String.valueOf(updatedTerm))
                        .param("value", String.valueOf(curvePointTest.getValue())))
                .andExpect(redirectedUrl("/curvePoint/list"));

        List<CurvePoint> list = curvePointService.getCurvePoints();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                cList -> cList.getTerm() == updatedTerm
                        && cList.getValue() == curvePointTest.getValue()
        ));
    }

    @Test
    public void deleteCurvePointTest() throws Exception {
        curvePointService.saveCurvePoint(curvePointTest);

        mockMvc.perform(get("/curvePoint/delete/" + curvePointTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(redirectedUrl("/curvePoint/list"));

        assertEquals(0, curvePointService.getCurvePoints().size());
    }
}
