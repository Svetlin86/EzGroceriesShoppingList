package com.ezgroceries.shoppinglist.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findCocktailList() throws Exception {

        mockMvc.perform(get("/cocktails?search=Russian").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

    }

    @Test
    public void createNewShoppingList() throws Exception {
        MvcResult result = mockMvc.perform(post("/shopping-lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"MyShoppingList\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().string("location", Matchers.containsString("MyShoppingList")))
                .andReturn();

    }

    @Test
    public void addCocktailToShoppingList() throws Exception {

        MvcResult result = mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails", "90689338-499a-4c49-af90-f1e73068ad4f")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shoppingListId\": \"90689338-499a-4c49-af90-f1e73068ad4f\" , \"cocktailId\":\"23b3d85a-3928-41c0-a533-6538a71e17c4\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void findShoppingListAndReturnAListOfIngredients() throws Exception {

        MvcResult result = mockMvc.perform(post("/shopping-lists/{shoppingListId}", "90689338-499a-4c49-af90-f1e73068ad4f")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shoppingListId\": \"90689338-499a-4c49-af90-f1e73068ad4f\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void findAllShoppingList() throws Exception {
        mockMvc.perform(get("/shopping-lists").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}