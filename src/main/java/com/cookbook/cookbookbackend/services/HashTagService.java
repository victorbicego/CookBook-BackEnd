package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.repositories.HashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class HashTagService {

    @Autowired
    private HashTagRepository hashTagRepository;

    public ResponseEntity<?> getAllHashTags() {
        return new ResponseEntity<>(hashTagRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> findHashTagById(Long id) {
        Optional<HashTag> hashTagOptional = hashTagRepository.findById(id);

        if (hashTagOptional.isPresent()) {
            return new ResponseEntity<>(hashTagOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> saveHashTag(HashTag hashTag) {
        return new ResponseEntity<>(hashTagRepository.save(hashTag), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteHashTag(Long id) {
        Optional<HashTag> hashTagOptional = hashTagRepository.findById(id);

        if (hashTagOptional.isPresent()) {
            hashTagRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateHashTag(Long id, HashTag hashTag) {
        Optional<HashTag> hashTagOptional = hashTagRepository.findById(id);

        if (hashTagOptional.isPresent()) {
            HashTag updatedHashTag = hashTagOptional.get();
            updatedHashTag.setText(hashTag.getText());
            return new ResponseEntity<>(hashTagRepository.save(updatedHashTag), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Optional<HashTag> findHashTagByText(String text) {
        return hashTagRepository.findHashTagByText(text);
    }

}
