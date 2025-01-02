package com.opcr.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "MoodysRating is mandatory.")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory.")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory.")
    private String fitchRating;

    @NotNull(message = "Order is mandatory.")
    @Positive(message = "Order must be positive.")
    private Integer orderNumber;

}