package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.CurvePoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CurvePointRepositoryTest {

    @Autowired
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setTerm(1);
        curvePoint.setValue(1);
    }

    @Test
    public void saveBidListTest() {
        curvePointRepository.save(curvePoint);
        assertTrue(curvePointRepository.findAll().contains(curvePoint));
        assertEquals(curvePointRepository.findAll().size(), 1);
    }

    @Test
    public void updateBidListTest() {
        curvePointRepository.save(curvePoint);

        CurvePoint update = curvePointRepository.findById(curvePoint.getId()).orElseThrow();
        update.setValue(30);
        curvePointRepository.save(update);

        CurvePoint toVerify = curvePointRepository.findById(update.getId()).orElseThrow();
        assertEquals(curvePointRepository.findAll().size(), 1);
        assertEquals(toVerify.getValue(), 30);
    }

    @Test
    public void deleteBidListTest() {
        curvePointRepository.save(curvePoint);
        assertEquals(curvePointRepository.findAll().size(), 1);
        curvePointRepository.deleteById(curvePoint.getId());
        assertEquals(curvePointRepository.findAll().size(), 0);
    }
}
