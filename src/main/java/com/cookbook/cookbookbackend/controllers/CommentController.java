package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.Comment;
import com.cookbook.cookbookbackend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/allbyrecipe/{id}")
    public ResponseEntity<?> findAllCommentsByRecipe(@PathVariable Long id) {
        return commentService.getAllCommentsByRecipe(id);
    }

    @PostMapping("/post")
    public ResponseEntity<?> saveComment(@RequestBody @Valid Comment comment) {
        return commentService.saveComment(comment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @GetMapping("/topbyrecipe/{id}")
    public ResponseEntity<?> findTopCommentByRecipe(@PathVariable Long id) {
        return commentService.findTopCommentByRecipe(id);
    }

}
