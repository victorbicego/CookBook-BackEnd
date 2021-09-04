package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.services.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/hashtag")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HashTagController {

    @Autowired
    private HashTagService hashTagService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllHashTags() {
        return hashTagService.getAllHashTags();
    }

    // Used just to Pre-populate!
    @GetMapping("/get/{id}")
    public ResponseEntity<?> findHashTagById(@PathVariable Long id) {
        return hashTagService.findHashTagById(id);
    }

    // Used just to Pre-populate!
    @PostMapping("/post")
    public ResponseEntity<?> saveHashTag(@RequestBody @Valid HashTag hashTag) {
        return hashTagService.saveHashTag(hashTag);
    }

    // Used just to Pre-populate!
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHashTag(@PathVariable Long id) {
        return hashTagService.deleteHashTag(id);
    }

    // Used just to Pre-populate!
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateHashTag(@PathVariable Long id, @RequestBody @Valid HashTag hashTag) {
        return hashTagService.updateHashTag(id, hashTag);
    }

}
