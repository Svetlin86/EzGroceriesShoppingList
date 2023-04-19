package com.ezgroceries.shoppinglist.dto;

import com.ezgroceries.shoppinglist.model.Cocktail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CocktailDto {

    private String id;
    private String cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private Set<String> ingredients;

    @JsonIgnore
    public Cocktail getCocktailEntity(){
        Cocktail cocktail = new Cocktail();
        cocktail.setId(id != null ? UUID.fromString(id) : UUID.randomUUID());
        cocktail.setIdDrink(cocktailId);
        cocktail.setName(name);
        cocktail.setGlass(glass);
        cocktail.setInstructions(instructions);
        cocktail.setImage(image);
        cocktail.setIngredients(ingredients);
        return cocktail;
    }
}