package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.BidList;
import com.opcr.poseidon.services.BidListService;
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

@WebMvcTest(BidListController.class)
public class BidListValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;

    @Test
    public void validate() throws Exception {
        doNothing().when(bidListService).saveBidList(any(BidList.class));
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "10"))
                .andExpect(redirectedUrl("/bidList/list"));
        verify(bidListService).saveBidList(any(BidList.class));
    }

    @Test
    public void whenAccountIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("type", "type")
                        .param("bidQuantity", "10"))
                .andExpect(content().string(containsString("Account is mandatory.")));
        verify(bidListService, never()).saveBidList(any(BidList.class));
    }

    @Test
    public void whenTypeIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("bidQuantity", "10"))
                .andExpect(content().string(containsString("Type is mandatory.")));
        verify(bidListService, never()).saveBidList(any(BidList.class));
    }

    @Test
    public void whenBidQuantityIsEmptyShowError() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("type", "type"))
                .andExpect(content().string(containsString("Bid Quantity must be at least 0.01.")));
        verify(bidListService, never()).saveBidList(any(BidList.class));
    }

    @Test
    public void whenBidQuantityIsNegativeShowError() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "-10"))
                .andExpect(content().string(containsString("Bid Quantity must be at least 0.01.")));
        verify(bidListService, never()).saveBidList(any(BidList.class));
    }
}
