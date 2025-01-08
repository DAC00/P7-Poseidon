package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade();
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(10);
    }

    @Test
    public void saveBidListTest() {
        tradeRepository.save(trade);
        assertTrue(tradeRepository.findAll().contains(trade));
        assertEquals(tradeRepository.findAll().size(), 1);
    }

    @Test
    public void updateBidListTest() {
        tradeRepository.save(trade);

        Trade update = tradeRepository.findById(trade.getId()).orElseThrow();
        update.setBuyQuantity(50);
        tradeRepository.save(update);

        Trade toVerify = tradeRepository.findById(update.getId()).orElseThrow();
        assertEquals(tradeRepository.findAll().size(), 1);
        assertEquals(toVerify.getBuyQuantity(), 50);
    }

    @Test
    public void deleteBidListTest() {
        tradeRepository.save(trade);
        assertEquals(tradeRepository.findAll().size(), 1);
        tradeRepository.deleteById(trade.getId());
        assertEquals(tradeRepository.findAll().size(), 0);
    }
}
