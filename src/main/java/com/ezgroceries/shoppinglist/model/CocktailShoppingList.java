package com.ezgroceries.shoppinglist.model;

// TODO potential import issue (if so use javax.persistence)

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cocktail_shopping_list")
public class CocktailShoppingList {

    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "shopping_list_id", nullable = false)
    UUID shoppingListId;

    @Column(name = "cocktail_id", nullable = false)
    UUID cocktailId;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id", referencedColumnName = "id",updatable = false,insertable = false)
    ShoppingList shoppingList;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cocktail_id", referencedColumnName = "id",updatable = false,insertable = false)
    Cocktail cocktail;
}
