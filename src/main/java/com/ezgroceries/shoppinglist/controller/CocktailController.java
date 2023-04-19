package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.dto.CocktailDto;
import com.ezgroceries.shoppinglist.out.CocktailDBClient;
import com.ezgroceries.shoppinglist.out.CocktailDBResponse;
import com.ezgroceries.shoppinglist.out.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.service.CocktailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CocktailController {
    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    private final CocktailDBClient cocktailDBClient;

    private final CocktailService cocktailService;

    @GetMapping("/cocktails")
    public ResponseEntity<List<CocktailDto>> findCocktailList(@RequestParam String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        List<DrinkResource> drinks = cocktailDBResponse.getDrinks();
        List<CocktailDto> cocktailDtoList = cocktailService.mergeCocktails(drinks);

        return ResponseEntity.ok(cocktailDtoList);
    }

    @GetMapping("/cocktails/{cocktailId}")
    public ResponseEntity<CocktailDto> findCocktailById(@PathVariable String cocktailId) throws Exception {
        CocktailDto cocktail = cocktailService.getCocktailById(UUID.fromString(cocktailId));

        return ResponseEntity.ok(cocktail);
    }
}
