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

    /**
     * Get all the Rating from de database.
     *
     * @return List of all the Rating.
     */
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    /**
     * Get the Rating with the id ratingId.
     *
     * @param ratingId of the Rating to find.
     * @return Rating with the id ratingId.
     */
    public Optional<Rating> getRatingById(Integer ratingId) {
        return ratingRepository.findById(ratingId);
    }

    /**
     * Save the Rating in the database.
     *
     * @param ratingToSave is the Rating to save.
     */
    public void saveRating(Rating ratingToSave) {
        ratingRepository.save(ratingToSave);
    }

    /**
     * Update the Rating with id ratingId.
     *
     * @param ratingId      of the Rating to update.
     * @param ratingUpdated is the Rating with updated information.
     */
    public void updateRatingById(Integer ratingId, Rating ratingUpdated) {
        Optional<Rating> oldRating = getRatingById(ratingId);
        if (oldRating.isPresent() && ratingId.equals(ratingUpdated.getId())) {
            ratingRepository.save(ratingUpdated);
        }
    }

    /**
     * Delete the Rating with id ratingId.
     *
     * @param ratingId of the Rating to delete.
     */
    public void deleteRatingById(Integer ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
