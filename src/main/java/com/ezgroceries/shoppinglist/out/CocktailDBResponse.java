package com.ezgroceries.shoppinglist.out;

import com.ezgroceries.shoppinglist.dto.CocktailDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
public class CocktailDBResponse {

    @Getter
    @Setter
    private List<DrinkResource> drinks;


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

        @JsonIgnore
        public Set<String> getIngredients() {
            return Stream.of(strIngredient1, strIngredient2, strIngredient3,
                    strIngredient4, strIngredient5).filter(StringUtils::isNotBlank).collect(Collectors.toSet());


        }

        @JsonIgnore
        public CocktailDto cocktailDto() {
            return  CocktailDto.builder()
                    .cocktailId(getIdDrink())
                    .glass(getStrGlass())
                    .ingredients(getIngredients())
                    .name(getStrDrink())
                    .image(getStrDrinkThumb())
                    .instructions(getStrInstructions()).build();
        }
    }
}
