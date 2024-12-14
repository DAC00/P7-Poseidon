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

    @NotBlank(message = "name is mandatory.")
    private String name;

    @NotBlank(message = "description is mandatory.")
    private String description;

    @NotBlank(message = "json is mandatory.")
    private String json;

    @NotBlank(message = "template is mandatory.")
    private String template;

    @NotBlank(message = "sqlStr is mandatory.")
    private String sqlStr;

    @NotBlank(message = "sqlPart is mandatory.")
    private String sqlPart;

}