package com.cookbook.cookbookbackend.models;

import com.cookbook.cookbookbackend.enums.Category;
import com.cookbook.cookbookbackend.enums.DifficultyGrade;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 30)
    private String author;

    @Size(min = 1, max = 800)
    private String instruction;

    @Min(value = 1)
    private Integer portion, preparationTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DifficultyGrade difficultyGrade;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @ManyToOne
    @NotNull
    private Cuisine cuisine;

    @ManyToMany
    private List<HashTag> hashTagList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @JsonManagedReference
    @NotNull
    private List<Ingredient> ingredientList;

    @NotNull
    private String dateOfPost;

    @Min(value = 0)
    @Max(value = 5)
    private Double rating;

}
