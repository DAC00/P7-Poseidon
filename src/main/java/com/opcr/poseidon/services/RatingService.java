package com.opcr.poseidon.services;

import com.opcr.poseidon.domain.Rating;
import com.opcr.poseidon.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> getRatingById(Integer idRating) {
        return ratingRepository.findById(idRating);
    }

    public void saveRating(Rating ratingToSave) {
        ratingRepository.save(ratingToSave);
    }

    public void updateRatingById(Integer idRating, Rating ratingUpdated) {
        Optional<Rating> oldRating = getRatingById(idRating);
        if (oldRating.isPresent() && idRating.equals(ratingUpdated.getId())) {
            ratingRepository.save(ratingUpdated);
        }
    }

    public void deleteRatingById(Integer idRating) {
        ratingRepository.deleteById(idRating);
    }
}
