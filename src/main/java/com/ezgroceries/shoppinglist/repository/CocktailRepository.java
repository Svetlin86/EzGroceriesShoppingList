package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.model.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CocktailRepository extends JpaRepository<Cocktail, UUID> {
     List<Cocktail> findByIdDrinkIn(List<String> ids);
}
