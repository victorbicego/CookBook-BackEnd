package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.services.HashTagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
public class HashTagControllerTests {

    @MockBean
    private HashTagService hashTagService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final HashTag hashTagTest1 = new HashTag();
    private final HashTag hashTagTest2 = new HashTag();

    @BeforeEach
    public void init() {
        hashTagTest1.setText("#lovetest1");
        hashTagTest1.setId(5L);
    }

    @Test
    @DisplayName("Test Post - OK")
    public void testPost() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagTest1, HttpStatus.CREATED);

        doReturn(responseEntityAnswer).when(hashTagService).saveHashTag(hashTagTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hashtag/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(hashTagTest1)));

    }

    @Test
    @DisplayName("Test Post - Invalid because is empty")
    public void testPostInvalidEmpty() throws Exception {
        hashTagTest1.setText("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hashtag/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because is longer than 20")
    public void testPostInvalidToLong() throws Exception {
        hashTagTest1.setText("#Lorem ipsum dolor dui");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hashtag/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Get By ID - OK")
    public void testGetOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).findHashTagById(5L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hashtag/get/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(hashTagTest1)));
    }

    @Test
    @DisplayName("Test Get By ID - NOTFOUND")
    public void testGetNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(hashTagService).findHashTagById(7L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hashtag/get/{id}", 7L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Get All Success")
    public void testGetAllSuccess() throws Exception {
        List<HashTag> hashTagList = new ArrayList<>(List.of(hashTagTest1, hashTagTest2));

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagList, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).getAllHashTags();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hashtag/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(hashTagList)));
    }

    @Test
    @DisplayName("Test Delete - OK")
    public void testDeleteOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).deleteHashTag(5L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/hashtag/delete/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test Delete - NOTFOUND")
    public void testDeleteNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(hashTagService).deleteHashTag(21L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/hashtag/delete/{id}", 21L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Put - OK")
    public void testPutOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).updateHashTag(5L, hashTagTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(hashTagTest1)));
    }

    @Test
    @DisplayName("Test Put - NOTFOUND")
    public void testPutNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(hashTagService).updateHashTag(25L, hashTagTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 25L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Put - Invalid because is Empty")
    public void testPutInvalidEmpty() throws Exception {
        hashTagTest1.setText("");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Put - Invalid because is longer than 20")
    public void testPutInvalidToLong() throws Exception {
        hashTagTest1.setText("#Lorem ipsum dolor dui");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
