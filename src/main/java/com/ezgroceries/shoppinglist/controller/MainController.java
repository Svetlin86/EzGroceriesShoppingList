package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.Cocktail;
import com.ezgroceries.shoppinglist.model.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.TreeSet;

@RestController
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/cocktails")
    public ResponseEntity<Set<Cocktail>> findCocktailList(@RequestParam String search) {

        Set<Cocktail> cocktails = Set.of(margerita, blueMargerita);

        if (cocktails.stream().allMatch(cocktail -> cocktail.getCategory().equals(search))) {
            return ResponseEntity.ok().body(cocktails);
        }

        throw new RuntimeException();
    }

    @PostMapping("/shopping-lists")
    public ResponseEntity<Void> createNewShoppingList(@RequestBody String name) {

        ShoppingList shoppingList = new ShoppingList().setName(name);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/shopping-lists/{name}")
                .buildAndExpand(shoppingList)
                .toUri();


        return ResponseEntity.created(location).build();
    }

    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@PathVariable String shoppingListId,
                                                          @RequestBody String cocktailId) {
        Set<Cocktail> cocktailsList = new TreeSet<>();

        if (shoppingListId.equals(myShoppingList.getShoppingListId()) && cocktailId.equals(margerita.getCocktailId())) {
            cocktailsList.add(margerita);
            myShoppingList.setCocktails(cocktailsList);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListId}")
                .buildAndExpand(margerita)
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @PostMapping("/shopping-lists/{shoppingListId}")
    public ResponseEntity<Void> findShoppingListAndReturnAListOfIngredients(@PathVariable String shoppingListId) {

        if (shoppingListId.equals(myShoppingList.getShoppingListId())) {

            for (Cocktail c : myShoppingList.getCocktails()) {
                myShoppingList.setListOfCocktailIngredientsInShoppingList(c.getIngredients());
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{listOfCocktailIngredientsInShoppingList}")
                    .buildAndExpand(myShoppingList.getListOfCocktailIngredientsInShoppingList())
                    .toUri();
            return ResponseEntity.created(location).build();
        }

        throw new RuntimeException();
    }

    @GetMapping("/shopping-lists")
    public ResponseEntity<Set<ShoppingList>> findAllShoppingList() {
        Set<ShoppingList> shoppingLists = Set.of(myShoppingList, mySecondShoppingList);
        return ResponseEntity.ok().body(shoppingLists);
    }


    /*****************************  Hard Coded Data  **************************************************/

    Cocktail margerita = new Cocktail().setCocktailId("23b3d85a-3928-41c0-a533-6538a71e17c4")
            .setName("Margerita")
            .setGlass("Cocktail glass")
            .setInstructions("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..")
            .setImage("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg")
            .setIngredients(Set.of("Tequila",
                    "Triple sec",
                    "Lime juice",
                    "Salt"))
            .setCategory("Russian");

    Cocktail blueMargerita = new Cocktail().setCocktailId("d615ec78-fe93-467b-8d26-5d26d8eab073")
            .setName("Blue Margerita")
            .setGlass("Cocktail glass")
            .setInstructions("Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..")
            .setImage("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg")
            .setIngredients(Set.of("Tequila",
                    "Blue Curacao",
                    "Lime juice",
                    "Salt"))
            .setCategory("Russian");

    ShoppingList myShoppingList = new ShoppingList().setShoppingListId("90689338-499a-4c49-af90-f1e73068ad4f")
            .setName("MyShoppingList").setCocktails(Set.of(margerita, blueMargerita));

    ShoppingList mySecondShoppingList = new ShoppingList().setShoppingListId("4ba92a46-1d1b-4e52-8e38-13cd56c7224c")
            .setName("My Birthday").setCocktails(Set.of(blueMargerita));
}
