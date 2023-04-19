package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.dto.CocktailDto;
import com.ezgroceries.shoppinglist.model.Cocktail;
import com.ezgroceries.shoppinglist.out.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public CocktailDto getCocktailById(UUID id) throws Exception {
        Cocktail cocktail = cocktailRepository.findById(id).orElseThrow(() -> new Exception("Cocktail not found"));

        return modelMapper.map(cocktail, CocktailDto.class);
    }


    public List<CocktailDto> mergeCocktails(List<DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, Cocktail> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(Cocktail::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, Cocktail> allEntityMap = drinks.stream().map(drinkResource -> {
            Cocktail cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                Cocktail newCocktail = new Cocktail();
                newCocktail.setId(UUID.randomUUID());
                newCocktail.setIdDrink(drinkResource.getIdDrink());
                newCocktail.setName(drinkResource.getStrDrink());
                newCocktail.setGlass(drinkResource.getStrGlass());
                newCocktail.setInstructions(drinkResource.getStrInstructions());
                newCocktail.setImage(drinkResource.getStrDrinkThumb());
                newCocktail.setIngredients(getIngredients(drinkResource));

                cocktailEntity = cocktailRepository.save(newCocktail);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(Cocktail::getIdDrink, o -> o, (o, o2) -> o));
        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailDto> mergeAndTransform(List<DrinkResource> drinks, Map<String, Cocktail> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailDto(allEntityMap
                        .get(drinkResource.getIdDrink()).getId().toString(),
                        drinkResource.getIdDrink(),
                        drinkResource.getStrDrink(),
                        drinkResource.getStrGlass(),
                        drinkResource.getStrInstructions(),
                        drinkResource.getStrDrinkThumb(),
                        getIngredients(drinkResource)))
                .collect(Collectors.toList());
    }

    private Set<String> getIngredients(DrinkResource drinkResource) {
        Set<String> ingredientsSet = new TreeSet<>();

        String ingredient1 = drinkResource.getStrIngredient1();
        String ingredient2 = drinkResource.getStrIngredient2();
        String ingredient3 = drinkResource.getStrIngredient3();
        String ingredient4 = drinkResource.getStrIngredient4();
        String ingredient5 = drinkResource.getStrIngredient5();

        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(ingredient1);
        ingredientsList.add(ingredient2);
        ingredientsList.add(ingredient3);
        ingredientsList.add(ingredient4);
        ingredientsList.add(ingredient5);

        for (String ingredient : ingredientsList) {
            if (ingredient != null) {
                ingredientsSet.add(ingredient);
            }
        }
        return ingredientsSet;
    }
}
