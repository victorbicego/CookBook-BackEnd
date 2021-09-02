package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.services.HashTagService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Test
    @DisplayName("Test Post - OK")
    public void testPost() throws Exception {
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest1");

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).saveHashTag(hashTagTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hashtag/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(hashTagTest1)));

    }

    @Test
    @DisplayName("Test Post - Invalid because is empty")
    public void testPostInvalidEmpty() throws Exception {
        HashTag hashTagTest1 = new HashTag();
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
        HashTag hashTagTest1 = new HashTag();
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
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest1");
        hashTagTest1.setId(5L);

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

        doReturn(responseEntityAnswer).when(hashTagService).findHashTagById(5L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hashtag/get/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Get All Success")
    public void testGetAllSuccess() throws Exception {
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest1");
        HashTag hashTagTest2 = new HashTag();
        hashTagTest2.setText("#lovetest2");
        HashTag hashTagTest3 = new HashTag();
        hashTagTest3.setText("#lovetest3");
        List<HashTag> hashTagList = new ArrayList<>(List.of(hashTagTest1, hashTagTest2, hashTagTest3));

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
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest1");
        hashTagTest1.setId(20L);

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).deleteHashTag(20L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/hashtag/delete/{id}", 20L)
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
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest");
        hashTagTest1.setId(20L);

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(hashTagTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(hashTagService).updateHashTag(20L, hashTagTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 20L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(hashTagTest1)));
    }

    @Test
    @DisplayName("Test Put - NOTFOUND")
    public void testPutNOTFOUND() throws Exception {
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest");
        hashTagTest1.setId(20L);

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
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("");
        hashTagTest1.setId(20L);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 20L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Put - Invalid because is longer than 20")
    public void testPutInvalidToLong() throws Exception {
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#Lorem ipsum dolor dui");
        hashTagTest1.setId(20L);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/hashtag/put/{id}", 20L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hashTagTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
