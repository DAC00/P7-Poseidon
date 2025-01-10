package com.opcr.poseidon.controllers;

import com.opcr.poseidon.domain.BidList;
import com.opcr.poseidon.services.BidListService;
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
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListService bidListService;

    private BidList bidListTest;

    @BeforeEach
    public void setUp() {
        this.bidListTest = new BidList();
        bidListTest.setAccount("test");
        bidListTest.setType("forTesting");
        bidListTest.setBidQuantity(91);
    }

    @Test
    public void saveBidListTest() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", bidListTest.getAccount())
                        .param("type", bidListTest.getType())
                        .param("bidQuantity", String.valueOf(bidListTest.getBidQuantity())))
                .andExpect(redirectedUrl("/bidList/list"));

        List<BidList> list = bidListService.getBidLists();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                bList -> bList.getAccount().equals(bidListTest.getAccount())
                        && bList.getType().equals(bidListTest.getType())
                        && bList.getBidQuantity() == bidListTest.getBidQuantity()
        ));
    }

    @Test
    public void updateBidListTest() throws Exception {
        bidListService.saveBidList(bidListTest);
        String updatedType = "updatedType";

        mockMvc.perform(post("/bidList/update/" + bidListTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("account", bidListTest.getAccount())
                        .param("type", updatedType)
                        .param("bidQuantity", String.valueOf(bidListTest.getBidQuantity())))
                .andExpect(redirectedUrl("/bidList/list"));

        List<BidList> list = bidListService.getBidLists();
        assertEquals(1, list.size());
        assertTrue(list.stream().anyMatch(
                bList -> bList.getAccount().equals(bidListTest.getAccount())
                        && bList.getType().equals(updatedType)
                        && bList.getBidQuantity() == bidListTest.getBidQuantity()
        ));
    }

    @Test
    public void deleteBidListTest() throws Exception {
        bidListService.saveBidList(bidListTest);

        mockMvc.perform(get("/bidList/delete/" + bidListTest.getId())
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(redirectedUrl("/bidList/list"));

        assertEquals(0, bidListService.getBidLists().size());
    }
}
