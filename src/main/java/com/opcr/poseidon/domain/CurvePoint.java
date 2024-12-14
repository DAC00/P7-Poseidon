package com.opcr.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer curveId;

    private LocalDateTime asOfDate;

    @NotBlank(message = "term is mandatory.")
    private double term;

    @NotBlank(message = "value is mandatory.")
    private double value;

    private LocalDateTime creationDate;

}