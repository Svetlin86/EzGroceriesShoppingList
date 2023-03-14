package com.ezgroceries.shoppinglist.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class ShoppingList {

    String shoppingListId;

    String name;

    Set<Cocktail> cocktails;

    Set<String> listOfCocktailIngredientsInShoppingList = new TreeSet<>();

}
