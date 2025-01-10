package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.Trade;
import com.opcr.poseidon.services.TradeService;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeService tradeService;

    private Trade tradeTest;

    @BeforeEach
    public void setUp() {
        this.tradeTest = new Trade();
        tradeTest.setAccount("Account");
        tradeTest.setType("Type");
        tradeTest.setBuyQuantity(50);
    }

    @Test
    public void saveTradeTest() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", tradeTest.getAccount())
                        .param("type", tradeTest.getType())
                        .param("buyQuantity", String.valueOf(tradeTest.getBuyQuantity())))
                .andExpect(redirectedUrl("/trade/list"));

        List<Trade> list = tradeService.getTrades();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                tList -> tList.getAccount().equals(tradeTest.getAccount())
                        && tList.getType().equals(tradeTest.getType())
                        && tList.getBuyQuantity() == tradeTest.getBuyQuantity()
        ));
    }

    @Test
    public void updateTradeTest() throws Exception {
        tradeService.saveTrade(tradeTest);
        String updatedType = "TEST";

        mockMvc.perform(post("/trade/update/" + tradeTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", tradeTest.getAccount())
                        .param("type", updatedType)
                        .param("buyQuantity", String.valueOf(tradeTest.getBuyQuantity())))
                .andExpect(redirectedUrl("/trade/list"));

        List<Trade> list = tradeService.getTrades();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                tList -> tList.getAccount().equals(tradeTest.getAccount())
                        && tList.getType().equals(updatedType)
                        && tList.getBuyQuantity() == tradeTest.getBuyQuantity()
        ));
    }

    @Test
    public void deleteTradeTest() throws Exception {
        tradeService.saveTrade(tradeTest);

        mockMvc.perform(get("/trade/delete/" + tradeTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(redirectedUrl("/trade/list"));

        assertEquals(0, tradeService.getTrades().size());
    }
}
