package com.group3.chefkoch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cuisine {
    @Id
    @GeneratedValue
    private Long id;
    private String cuisineName;
}
