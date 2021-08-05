package com.group3.chefkoch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String commentText, dateOfPost, Author;
    private Integer Rating;
    @ManyToOne
    private Recipe recipe;
}
