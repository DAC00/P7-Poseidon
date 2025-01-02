package com.opcr.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Account is mandatory.")
    private String account;

    @Column(nullable = false)
    @NotBlank(message = "Type is mandatory.")
    private String type;

    @DecimalMin(value = "0.01", message = "BuyQuantity must be at least 0.01.")
    private double buyQuantity;

    private double sellQuantity;

    private double buyPrice;

    private double sellPrice;

    private LocalDateTime tradeDate;

    private String security;

    private String status;

    private String trader;

    private String benchmark;

    private String book;

    private String creationName;

    private LocalDateTime creationDate;

    private String revisionName;

    private LocalDateTime revisionDate;

    private String dealName;

    private String dealType;

    private String sourceListId;

    private String side;

}