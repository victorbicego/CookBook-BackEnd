package com.cookbook.cookbookbackend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min =1, max=450)
    private String text;

    @Size(min = 1, max = 30)
    private String author;

    @Min(value = 0)
    private Integer rating;

    @ManyToOne
    @NotNull
    private Recipe recipe;

    @NotNull
    private String dateOfPost;

}
