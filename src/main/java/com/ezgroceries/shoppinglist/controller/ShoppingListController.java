package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShoppingListController {

    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    private final ShoppingListService shoppingListService;

    @GetMapping("/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingListDto> findShoppingListById(@PathVariable String shoppingListId) throws  Exception{
        ShoppingListDto shoppingListDto = shoppingListService.getShoppingListById(shoppingListId);
        return ResponseEntity.ok(shoppingListDto);
    }

    @GetMapping("/shopping-lists")
    public ResponseEntity<List<ShoppingListDto>> findAllShoppingList() throws Exception {
        List<ShoppingListDto> resourceList = shoppingListService.getAllShoppingLists();
        if (resourceList.isEmpty()) {
            throw new Exception("List of resources is empty");
        }

        return ResponseEntity.ok(resourceList);
    }

    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@PathVariable String shoppingListId,
                                                          @RequestParam String cocktails) throws Exception {

        ShoppingListDto shoppingListDto = shoppingListService.addCocktailToShoppingList(shoppingListId, Set.of(cocktails));

        return location(shoppingListDto.getId());
    }
    @PostMapping("/shopping-lists")
    public ResponseEntity<Void> createNewShoppingList(@RequestBody ShoppingListDto shoppingListDto) {

        ShoppingListDto newShoppingListDto = shoppingListService.create(shoppingListDto);
        newShoppingListDto.setId(UUID.randomUUID().toString());

        return location(shoppingListDto.getId());
    }



    private ResponseEntity<Void> location(String resourceId) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(resourceId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
