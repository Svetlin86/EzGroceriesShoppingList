package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.service.ShoppingListCocktailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("ShoppingListCocktailController tests")
public class ShoppingListCocktailControllerTest {

    @Mock
    private ShoppingListCocktailService shoppingListCocktailService;

    @InjectMocks
    private ShoppingListCocktailController shoppingListCocktailController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get distinct ingredients from cocktails in shopping list")
    public void testGetDistinctIngredientsFromCocktails() {
        // Arrange
        UUID shoppingListId = UUID.randomUUID();
        Set<Set<String>> expectedIngredients = new HashSet<>();
        expectedIngredients.add(Set.of("Gin", "Lemon Juice", "Simple Syrup", "Egg White", "Ice", "Lemon Peel"));
        expectedIngredients.add(Set.of("Gin", "Lime Juice", "Mint Leaves", "Club Soda", "Sugar Syrup", "Ice", "Salt"));

        when(shoppingListCocktailService.getDistinctIngredientsFromCocktails(shoppingListId)).thenReturn(expectedIngredients);

        // Act
        ResponseEntity<Set<Set<String>>> responseEntity = shoppingListCocktailController.getDistinctIngredientsFromCocktails(shoppingListId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedIngredients, responseEntity.getBody());
    }
}
