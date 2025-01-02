package com.opcr.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
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

    @DecimalMin(value = "0.01", message = "Term must be at least 0.01.")
    private double term;

    @DecimalMin(value = "0.01", message = "Value must be at least 0.01.")
    private double value;

    private LocalDateTime creationDate;

}