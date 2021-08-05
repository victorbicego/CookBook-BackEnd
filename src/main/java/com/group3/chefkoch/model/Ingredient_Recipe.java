package com.group3.chefkoch.model;

import com.group3.chefkoch.enums.Unit;

import javax.persistence.*;

@Entity
public class Ingredient_Recipe {
    @Id
    @GeneratedValue
    private Long id;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Unit unit;
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Ingredient ingredient;

}
