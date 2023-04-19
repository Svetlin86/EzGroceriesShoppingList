package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.model.CocktailShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShoppingListCocktailRepository extends JpaRepository<CocktailShoppingList, UUID> {
    List<CocktailShoppingList> findByShoppingListId(UUID shoppingListId);
}
