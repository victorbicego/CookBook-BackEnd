package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.services.CuisineService;
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
public class CuisineControllerTests {

    @MockBean
    private CuisineService cuisineService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Cuisine cuisineTest1 = new Cuisine();
    private final Cuisine cuisineTest2 = new Cuisine();

    @BeforeEach
    public void init(){
        cuisineTest1.setCountry("countryTest1");
        cuisineTest1.setContinent("continentTest1");
        cuisineTest1.setId(5L);
    }

    @Test
    @DisplayName("Test Post - OK")
    public void testPost() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(cuisineTest1, HttpStatus.CREATED);

        doReturn(responseEntityAnswer).when(cuisineService).saveCuisine(cuisineTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/cuisine/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cuisineTest1)));

    }

    @Test
    @DisplayName("Test Post - Invalid because country is empty")
    public void testPostInvalidCountryEmpty() throws Exception {
        cuisineTest1.setCountry("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/cuisine/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because country is longer than 50")
    public void testPostInvalidCountryToLong() throws Exception {
        cuisineTest1.setCountry("Lorem ipsum dolor sit amet, consectetur erat curae.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/cuisine/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because continent is empty")
    public void testPostInvalidContinentEmpty() throws Exception {
        cuisineTest1.setContinent("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/cuisine/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because continent is longer than 50")
    public void testPostInvalidContinentToLong() throws Exception {
        cuisineTest1.setContinent("Lorem ipsum dolor sit amet, consectetur erat curae.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/cuisine/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Get By ID - OK")
    public void testGetOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(cuisineTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(cuisineService).findCuisineById(5L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cuisine/get/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cuisineTest1)));
    }

    @Test
    @DisplayName("Test Get By ID - NOTFOUND")
    public void testGetNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(cuisineService).findCuisineById(5L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cuisine/get/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Get All Success")
    public void testGetAllSuccess() throws Exception {
        List<Cuisine> cuisineList = new ArrayList<>(List.of(cuisineTest1,cuisineTest2));

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(cuisineList, HttpStatus.OK);
        doReturn(responseEntityAnswer).when(cuisineService).getAllCuisines();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cuisine/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cuisineList)));
    }

    @Test
    @DisplayName("Test Delete - OK")
    public void testDeleteOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);

        doReturn(responseEntityAnswer).when(cuisineService).deleteCuisine(5L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/cuisine/delete/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test Delete - NOTFOUND")
    public void testDeleteNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(cuisineService).deleteCuisine(21L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/cuisine/delete/{id}", 21L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Put - OK")
    public void testPutOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(cuisineTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(cuisineService).updateCuisine(5L, cuisineTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/cuisine/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cuisineTest1)));
    }

    @Test
    @DisplayName("Test Put - NOTFOUND")
    public void testPutNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(cuisineService).updateCuisine(25L, cuisineTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/cuisine/put/{id}", 25L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Put - Invalid because country is Empty")
    public void testPutInvalidCountryEmpty() throws Exception {
        cuisineTest1.setCountry("");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/cuisine/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Put - Invalid because country is longer than 50")
    public void testPutInvalidCountryToLong() throws Exception {
        cuisineTest1.setCountry("Lorem ipsum dolor sit amet, consectetur erat curae.");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/cuisine/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Put - Invalid because continent is Empty")
    public void testPutInvalidContinentEmpty() throws Exception {
        cuisineTest1.setContinent("");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/cuisine/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Put - Invalid because continent is longer than 50")
    public void testPutInvalidContinentToLong() throws Exception {
        cuisineTest1.setContinent("Lorem ipsum dolor sit amet, consectetur erat curae.");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/cuisine/put/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuisineTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
