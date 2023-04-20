package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.model.Cocktail;
import com.ezgroceries.shoppinglist.model.CocktailShoppingList;
import com.ezgroceries.shoppinglist.repository.ShoppingListCocktailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ShoppingListCocktailServiceTest {

    private ShoppingListCocktailService shoppingListCocktailService;

    @Mock
    private ShoppingListCocktailRepository shoppingListCocktailRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingListCocktailService = new ShoppingListCocktailService(shoppingListCocktailRepository);
    }

    @Test
    public void getDistinctIngredientsFromCocktails() {
        UUID shoppingListId = UUID.randomUUID();
        List<CocktailShoppingList> shoppingListCocktails = new ArrayList<>();

        CocktailShoppingList cocktail1 = new CocktailShoppingList();
        cocktail1.setId(UUID.randomUUID());
        cocktail1.setShoppingListId(shoppingListId);
        Cocktail cocktail1Data = new Cocktail();
        cocktail1Data.setIdDrink("123456");
        cocktail1Data.setIngredients(new HashSet<>(Arrays.asList("Vodka", "Lime Juice", "Cranberry Juice")));
        cocktail1.setCocktail(cocktail1Data);
        shoppingListCocktails.add(cocktail1);

        CocktailShoppingList cocktail2 = new CocktailShoppingList();
        cocktail2.setId(UUID.randomUUID());
        cocktail2.setShoppingListId(shoppingListId);
        Cocktail cocktail2Data = new Cocktail();
        cocktail2Data.setIdDrink("654321");
        cocktail2Data.setIngredients(new HashSet<>(Arrays.asList("Rum", "Ginger Beer", "Lime Juice", "Mint Leaves")));
        cocktail2.setCocktail(cocktail2Data);
        shoppingListCocktails.add(cocktail2);

        when(shoppingListCocktailRepository.findByShoppingListId(shoppingListId)).thenReturn(shoppingListCocktails);

        Set<Set<String>> expectedIngredients = new HashSet<>();
        expectedIngredients.add(new HashSet<>(Arrays.asList("Vodka", "Lime Juice", "Cranberry Juice")));
        expectedIngredients.add(new HashSet<>(Arrays.asList("Rum", "Ginger Beer", "Lime Juice", "Mint Leaves")));

        Set<Set<String>> result = shoppingListCocktailService.getDistinctIngredientsFromCocktails(shoppingListId);

        assertEquals(expectedIngredients.size(), result.size());
        assertTrue(result.containsAll(expectedIngredients));
    }
}
