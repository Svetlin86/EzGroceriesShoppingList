package com.ezgroceries.shoppinglist.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

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

    String ingredient1;

    String ingredient2;

    String ingredient3;

    String ingredient4;

    String ingredient5;
    Set<String> ingredients;
}
