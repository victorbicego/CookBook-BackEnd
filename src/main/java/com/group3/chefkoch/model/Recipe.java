package com.group3.chefkoch.model;

import com.group3.chefkoch.enums.Category;
import com.group3.chefkoch.enums.DifficultyGrade;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;
    private String recipeName, author, instruction, dateOfPost;
    private Integer calorien, portion, preparationTime, cookTime, waitTime;
    @Enumerated(EnumType.STRING)
    private DifficultyGrade difficultyGrade;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne
    private Cuisine cuisine;
    @ManyToMany
    private List<HashTag> hashTagList;

    //***********************???Bilder????*************************
}
