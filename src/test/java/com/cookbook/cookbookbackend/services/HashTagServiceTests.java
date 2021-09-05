package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.repositories.HashTagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HashTagServiceTests {

    @Mock
    private HashTagRepository hashTagRepository;

    @InjectMocks
    private HashTagService hashTagService;

    private final HashTag hashTagTest1 = new HashTag();

    @BeforeEach
    public void init() {
        hashTagTest1.setText("#lovetest1");
        hashTagTest1.setId(5L);

        when(hashTagRepository.findById(5L)).thenReturn(Optional.of(hashTagTest1));
        when(hashTagRepository.findById(10L)).thenReturn(Optional.empty());
        when(hashTagRepository.save(hashTagTest1)).thenReturn(hashTagTest1);
    }

    @Test
    @DisplayName("Test findHashTagByID - OK")
    public void testFindByIdOK() {
        Optional<HashTag> hashTagOptional = Optional.of(hashTagTest1);
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagOptional.get(), HttpStatus.OK);

        assertEquals(responseEntityAnswer, hashTagService.findHashTagById(5L));
    }

    @Test
    @DisplayName("Test findHashTagByID - NOT FOUND")
    public void testFindByIdNOTFOUND() {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        assertEquals(responseEntityAnswer, hashTagService.findHashTagById(10L));
    }

    @Test
    @DisplayName("Test delete HashTag - OK")
    public void testDeleteOK() {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);

        assertEquals(responseEntityAnswer, hashTagService.deleteHashTag(5L));
    }

    @Test
    @DisplayName("Test delete - NOT FOUND")
    public void testDeleteNOTFOUND() {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        assertEquals(responseEntityAnswer, hashTagService.deleteHashTag(10L));
    }

    @Test
    @DisplayName("Test update HashTag - OK")
    public void testUpdateOK() {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagTest1, HttpStatus.OK);

        assertEquals(responseEntityAnswer, hashTagService.updateHashTag(5L, hashTagTest1));
    }

    @Test
    @DisplayName("Test update - NOT FOUND")
    public void testUpdateNOTFOUND() {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        assertEquals(responseEntityAnswer, hashTagService.updateHashTag(10L, hashTagTest1));
    }
    
}
