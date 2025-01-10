package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.Trade;
import com.opcr.poseidon.services.TradeService;
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

@WebMvcTest(TradeController.class)
public class TradeValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    @Test
    public void validate() throws Exception {
        doNothing().when(tradeService).saveTrade(any(Trade.class));
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "1"))
                .andExpect(redirectedUrl("/trade/list"));
        verify(tradeService).saveTrade(any(Trade.class));
    }

    @Test
    public void whenAccountIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("type", "type")
                        .param("buyQuantity", "1"))
                .andExpect(content().string(containsString("Account is mandatory.")));
        verify(tradeService, never()).saveTrade(any(Trade.class));
    }

    @Test
    public void whenTypeIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("buyQuantity", "1"))
                .andExpect(content().string(containsString("Type is mandatory.")));
        verify(tradeService, never()).saveTrade(any(Trade.class));
    }

    @Test
    public void whenBuyQuantityIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("type", "type"))
                .andExpect(content().string(containsString("BuyQuantity must be at least 0.01.")));
        verify(tradeService, never()).saveTrade(any(Trade.class));
    }

    @Test
    public void whenBuyQuantityIsNegativeShowError() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "-10"))
                .andExpect(content().string(containsString("BuyQuantity must be at least 0.01.")));
        verify(tradeService, never()).saveTrade(any(Trade.class));
    }
}
