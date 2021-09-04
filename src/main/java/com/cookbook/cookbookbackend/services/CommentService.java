package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.models.Comment;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.repositories.CommentRepository;
import com.cookbook.cookbookbackend.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    public ResponseEntity<?> getAllCommentsByRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            Recipe foundRecipe = recipeOptional.get();
            List<Comment> commentList = commentRepository.findAllCommentsByRecipe(foundRecipe);
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> saveComment(Comment comment) {
        Comment newComment = commentRepository.save(comment);
        recipeService.updateRating(newComment.getRecipe());
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if (commentOptional.isPresent()) {
            commentRepository.deleteById(id);
            recipeService.updateRating(commentOptional.get().getRecipe());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteAllCommentsByRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            Recipe foundRecipe = recipeOptional.get();
            List<Comment> recipeComments = commentRepository.findAllCommentsByRecipe(foundRecipe);
            commentRepository.deleteAll(recipeComments);
        }

    }

    public ResponseEntity<?> findTopCommentByRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            Recipe foundRecipe = recipeOptional.get();
            Comment topComment = commentRepository.findTopCommentByRecipeOrderByRatingDesc(foundRecipe);
            return new ResponseEntity<>(topComment, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Comment> findAllCommentsByRecipe(Recipe recipe) {
        return commentRepository.findAllCommentsByRecipe(recipe);
    }

}
