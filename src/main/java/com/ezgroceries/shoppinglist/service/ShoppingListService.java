package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.model.CocktailShoppingList;
import com.ezgroceries.shoppinglist.model.ShoppingList;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListCocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;
    private final ShoppingListCocktailRepository shoppingListCocktailRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ShoppingListDto getShoppingListById(String id) throws Exception {
        ShoppingList shoppingList = shoppingListRepository.findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new Exception("")
                );
        return modelMapper.map(shoppingList, ShoppingListDto.class);
    }

    public List<ShoppingListDto> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
        return shoppingLists.stream()
                .map(shoppingList -> modelMapper.map(shoppingList, ShoppingListDto.class))
                .toList();
    }

    public ShoppingListDto create(@NonNull @RequestBody ShoppingListDto shoppingListDto) {
        ShoppingList shoppingList = modelMapper.map(shoppingListDto, ShoppingList.class);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        return modelMapper.map(savedShoppingList, ShoppingListDto.class);
    }

    public ShoppingListDto addCocktailToShoppingList(String shoppingListId, Set<String> cocktailIds) throws Exception {

        for (String cocktailId : cocktailIds) {
            cocktailRepository.findById(UUID.fromString(cocktailId)).orElseThrow(
                    () -> new Exception("No such cocktail")
            );

            CocktailShoppingList cocktailShoppingList = new CocktailShoppingList();
            cocktailShoppingList.setShoppingListId(UUID.fromString(shoppingListId));
            cocktailShoppingList.setCocktailId(UUID.fromString(cocktailId));
            shoppingListCocktailRepository.save(cocktailShoppingList);
        }
        return shoppingListRepository.findById(UUID.fromString(shoppingListId)).orElseThrow(
                () -> new Exception("No such shopping list")
        ).shoppingListDto();
    }

}