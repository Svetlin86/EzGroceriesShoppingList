package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.model.CocktailShoppingList;
import com.ezgroceries.shoppinglist.repository.ShoppingListCocktailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingListCocktailService {
    private final ShoppingListCocktailRepository shoppingListCocktailRepository;

    public Set<Set<String>> getDistinctIngredientsFromCocktails(UUID listId) {
        List<CocktailShoppingList> shoppingListCocktails = shoppingListCocktailRepository.findByShoppingListId(listId);
        return shoppingListCocktails.stream()
                .map(x -> x.getCocktail().getIngredients())
                .collect(Collectors.toSet());
    }
}
