package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.Comment;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.services.CommentService;
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
public class CommentControllerTests {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Recipe recipeTest = new Recipe();
    private final Comment commentTest1 = new Comment();
    private final Comment commentTest2= new Comment();

    @BeforeEach
    public void ini(){
        commentTest1.setText("Test1");
        commentTest1.setAuthor("Tester1");
        commentTest1.setRating(1);
        commentTest1.setDateOfPost("00/00/0000");
        commentTest1.setRecipe(recipeTest);
    }

    @Test
    @DisplayName("Test Post - OK")
    public void testPost() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(commentTest1, HttpStatus.CREATED);

        doReturn(responseEntityAnswer).when(commentService).saveComment(commentTest1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(commentTest1)));

    }

    @Test
    @DisplayName("Test Post - Invalid because text is empty")
    public void testPostInvalidTextEmpty() throws Exception {
        commentTest1.setText("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because text is longer than 450")
    public void testPostInvalidToLong() throws Exception {
        commentTest1.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce elit nisi, porta quis nibh sed, dignissim venenatis sapien. Fusce vitae ligula a purus imperdiet pulvinar venenatis non erat. Cras interdum odio nec maximus tincidunt. Quisque eget eros molestie, posuere risus non, vehicula velit. Nulla auctor, dui eget malesuada iaculis, sapien dui faucibus erat, eu tincidunt lectus elit vel urna. Ut iaculis, orci rutrum mollis ultricies, diam ex egestas.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because author is empty")
    public void testPostInvalidAuthorEmpty() throws Exception {
        commentTest1.setAuthor("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because author is longer than 30")
    public void testPostInvalidAuthorToLong() throws Exception {
        commentTest1.setAuthor("Lorem ipsum dolor sit amet vivamus.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because rating is smaller than 0")
    public void testPostInvalidRatingEmpty() throws Exception {
        commentTest1.setRating(-1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because dateOfPost is empty")
    public void testPostInvalidDateOfPostEmpty() throws Exception {
        commentTest1.setDateOfPost(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because recipe is empty")
    public void testPostInvalidRecipeEmpty() throws Exception {
        commentTest1.setRecipe(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Delete - OK")
    public void testDeleteOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);

        doReturn(responseEntityAnswer).when(commentService).deleteComment(20L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/comments/delete/{id}", 20L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test Delete - NOTFOUND")
    public void testDeleteNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(commentService).deleteComment(21L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/comments/delete/{id}", 21L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Get top by recipe - OK")
    public void testGetTopByRecipeOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(commentTest1, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(commentService).findTopCommentByRecipe(10L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comments/topbyrecipe/{id}", 10L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(commentTest1)));
    }

    @Test
    @DisplayName("Test Get top by recipe - NOT FOUND")
    public void testGetTopByRecipeNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(commentService).findTopCommentByRecipe(10L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comments/topbyrecipe/{id}", 10L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test Get all by recipe - OK")
    public void testGetAllByRecipeOK() throws Exception {
        List<Comment> commentList = new ArrayList<>(List.of(commentTest1, commentTest2));

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(commentList, HttpStatus.OK);

        doReturn(responseEntityAnswer).when(commentService).getAllCommentsByRecipe(10L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comments/allbyrecipe/{id}", 10L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(commentList)));
    }

    @Test
    @DisplayName("Test Get all by recipe - NOT FOUND")
    public void testGetAllByRecipeNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(commentService).getAllCommentsByRecipe(10L);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comments/allbyrecipe/{id}", 10L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
