package com.ezgroceries.shoppinglist.dto;

import com.ezgroceries.shoppinglist.model.ShoppingList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingListDto {

    private String id;
    private String name;
    private Set<CocktailDto> cocktails;
    private Set<String> ingredients;

    @JsonIgnore
    public ShoppingList getShoppingListEntity() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(id != null ? UUID.fromString(id) : UUID.randomUUID());
        shoppingList.setName(getName());
        return shoppingList;
    }
}