package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.service.ShoppingListCocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/shopping-lists/{shoppingListId}/cocktails")
@RequiredArgsConstructor
public class ShoppingListCocktailController {


    private final ShoppingListCocktailService shoppingListCocktailService;


    @GetMapping("/ingredients")
    public ResponseEntity<Set<Set<String>>> getDistinctIngredientsFromCocktails(@PathVariable("shoppingListId") UUID shoppingListId) {
        Set<Set<String>> ingredients = shoppingListCocktailService.getDistinctIngredientsFromCocktails(shoppingListId);
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }
}
