package com.cookbook.cookbookbackend.models;

import com.cookbook.cookbookbackend.enums.Unit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 30)
    @NotNull
    private String name;

    @Min(value = 1/4)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Unit unit;

    @ManyToOne
    @JsonBackReference
    private Recipe recipe;

}

