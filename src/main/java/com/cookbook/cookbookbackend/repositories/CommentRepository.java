package com.cookbook.cookbookbackend.repositories;

import com.cookbook.cookbookbackend.models.Comment;
import com.cookbook.cookbookbackend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllCommentsByRecipe(Recipe recipe);

    Comment findTopCommentByRecipeOrderByRatingDesc(Recipe recipe);

}
