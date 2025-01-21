package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
