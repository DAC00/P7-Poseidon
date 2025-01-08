package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
        rating.setFitchRating("fitch");
        rating.setMoodysRating("moody");
        rating.setSandPRating("sand");
        rating.setOrderNumber(1);
    }

    @Test
    public void saveBidListTest() {
        ratingRepository.save(rating);
        assertTrue(ratingRepository.findAll().contains(rating));
        assertEquals(ratingRepository.findAll().size(), 1);
    }

    @Test
    public void updateBidListTest() {
        ratingRepository.save(rating);

        Rating update = ratingRepository.findById(rating.getId()).orElseThrow();
        update.setOrderNumber(30);
        ratingRepository.save(update);

        Rating toVerify = ratingRepository.findById(update.getId()).orElseThrow();
        assertEquals(ratingRepository.findAll().size(), 1);
        assertEquals(toVerify.getOrderNumber(), 30);
    }

    @Test
    public void deleteBidListTest() {
        ratingRepository.save(rating);
        assertEquals(ratingRepository.findAll().size(), 1);
        ratingRepository.deleteById(rating.getId());
        assertEquals(ratingRepository.findAll().size(), 0);
    }
}
