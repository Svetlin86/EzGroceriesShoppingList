package com.ezgroceries.shoppinglist.model;

import com.ezgroceries.shoppinglist.dto.ShoppingListDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String name;

    @OneToMany(mappedBy = "shoppingList")
    Set<CocktailShoppingList> cocktailShoppingLists;

    @JsonIgnore
    public ShoppingListDto shoppingListDto() {
        return ShoppingListDto.builder()
                .id(id.toString())
                .name(name)
                .cocktails(cocktailShoppingLists.stream()
                        .map(CocktailShoppingList::getCocktail).map(Cocktail::getCocktailDto).collect(Collectors.toSet()))
                .ingredients(cocktailShoppingLists.stream().map(CocktailShoppingList::getCocktail).flatMap(str -> str.getIngredients().stream()).collect(Collectors.toSet()))
                .build();
    }
}
