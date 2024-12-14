package com.opcr.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "moodysRating is mandatory.")
    private String moodysRating;

    @NotBlank(message = "sandPRating is mandatory.")
    private String sandPRating;

    @NotBlank(message = "fitchRating is mandatory.")
    private String fitchRating;

    @Positive(message = "orderNumber must be positive.")
    @NotBlank(message = "orderNumber is mandatory.")
    private Integer orderNumber;

}