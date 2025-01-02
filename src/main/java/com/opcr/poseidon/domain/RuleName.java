package com.opcr.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotBlank(message = "Description is mandatory.")
    private String description;

    @NotBlank(message = "Json is mandatory.")
    private String json;

    @NotBlank(message = "Template is mandatory.")
    private String template;

    @NotBlank(message = "SQL is mandatory.")
    private String sqlStr;

    @NotBlank(message = "SQL Part is mandatory.")
    private String sqlPart;

}