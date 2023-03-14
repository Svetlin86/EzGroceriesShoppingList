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
public class Cocktail {

    String cocktailId;
    String name;
    String glass;
    String instructions;
    String image;
    Set<String> ingredients = new TreeSet<>();
    String category;
}
