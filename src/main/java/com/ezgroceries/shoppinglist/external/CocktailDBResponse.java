package com.ezgroceries.shoppinglist.external;

import com.ezgroceries.shoppinglist.model.Cocktail;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
public class CocktailDBResponse {

    private Set<DrinkResource> drinks;
    public static Cocktail toCocktail(DrinkResource resource) {

        return new Cocktail()
                .setCocktailId(resource.getIdDrink())
                .setName(resource.getStrDrink())
                .setGlass(resource.getStrGlass())
                .setInstructions(resource.getStrInstructions())
                .setImage(resource.getStrDrinkThumb())
                .setIngredient1(resource.getStrIngredient1())
                .setIngredient2(resource.getStrIngredient2())
                .setIngredient3(resource.getStrIngredient3())
                .setIngredient4(resource.getStrIngredient4())
                .setIngredient5(resource.getStrIngredient5());
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class DrinkResource {
        private String idDrink;
        private String strDrink;
        private String strGlass;
        private String strInstructions;
        private String strDrinkThumb;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;

        private String strIngredient4;

        private String strIngredient5;

        private Set<String> ingredients;

    }
}
