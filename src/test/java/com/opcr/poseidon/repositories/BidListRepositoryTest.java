package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.BidList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BidListRepositoryTest {

    @Autowired
    private BidListRepository bidListRepository;

    private BidList bidList;

    @BeforeEach
    public void setUp(){
        bidList = new BidList();
        bidList.setAccount("TEST");
        bidList.setType("type");
        bidList.setBidQuantity(10);
    }

    @Test
    public void saveBidListTest(){
        bidListRepository.save(bidList);
        assertTrue(bidListRepository.findAll().contains(bidList));
        assertEquals(bidListRepository.findAll().size(),1);
    }

    @Test
    public void updateBidListTest(){
        bidListRepository.save(bidList);

        BidList update = bidListRepository.findById(bidList.getId()).orElseThrow();
        update.setBidQuantity(30);
        bidListRepository.save(update);

        BidList toVerify = bidListRepository.findById(update.getId()).orElseThrow();
        assertEquals(bidListRepository.findAll().size(),1);
        assertEquals(toVerify.getBidQuantity(),30);
    }

    @Test
    public void deleteBidListTest(){
        bidListRepository.save(bidList);
        assertEquals(bidListRepository.findAll().size(),1);
        bidListRepository.deleteById(bidList.getId());
        assertEquals(bidListRepository.findAll().size(),0);
    }
}
