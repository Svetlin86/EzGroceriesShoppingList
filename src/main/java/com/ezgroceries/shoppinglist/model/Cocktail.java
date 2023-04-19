package com.ezgroceries.shoppinglist.model;

import com.ezgroceries.shoppinglist.dto.CocktailDto;
import com.ezgroceries.shoppinglist.utilities.StringSetConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cocktail")
public class Cocktail {

    @Id
    UUID id;

    @Column(name = "id_drink")
    String idDrink;
    String name;

    String glass;

    String instructions;

    String image;

    @Convert(converter = StringSetConverter.class)
    Set<String> ingredients;

    @OneToMany(mappedBy = "cocktail")
    Set<CocktailShoppingList> cocktailShoppingLists;

    @JsonIgnore
    public CocktailDto getCocktailDto() {
        return CocktailDto.builder()
                .id(getId().toString())
                .cocktailId(getIdDrink())
                .name(getName())
                .image(getImage())
                .glass(getGlass())
                .instructions(getInstructions())
                .ingredients(getIngredients()).build();
    }
}
