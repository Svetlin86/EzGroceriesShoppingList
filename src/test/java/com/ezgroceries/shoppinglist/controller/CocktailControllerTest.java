package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.dto.CocktailDto;
import com.ezgroceries.shoppinglist.out.CocktailDBClient;
import com.ezgroceries.shoppinglist.out.CocktailDBResponse;
import com.ezgroceries.shoppinglist.out.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.service.CocktailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CocktailControllerTest {

    @Mock
    private CocktailDBClient cocktailDBClient;

    @Mock
    private CocktailService cocktailService;

    @Test
    void findCocktailList() {
        // Given
        String search = "gin";
        DrinkResource drinkResource1 = new DrinkResource();
        drinkResource1.setIdDrink(UUID.randomUUID().toString());
        DrinkResource drinkResource2 = new DrinkResource();
        drinkResource2.setIdDrink(UUID.randomUUID().toString());
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        cocktailDBResponse.setDrinks(Arrays.asList(drinkResource1, drinkResource2));
        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(cocktailDBResponse);

        CocktailDto cocktailDto1 = new CocktailDto(UUID.randomUUID().toString(),"1233F", "Gin Fizz", "500ml", "prepare",
                "image-url", Set.of("cola","jin"));
        CocktailDto cocktailDto2 = new CocktailDto(UUID.randomUUID().toString(),"1233o", "Vodka", "800ml", "prepare",
                "image-url", Set.of("cola","vodka"));
        List<CocktailDto> expectedCocktails = Arrays.asList(cocktailDto1, cocktailDto2);
        when(cocktailService.mergeCocktails(cocktailDBResponse.getDrinks())).thenReturn(expectedCocktails);

        CocktailController cocktailController = new CocktailController(cocktailDBClient, cocktailService);

        // When
        ResponseEntity<List<CocktailDto>> responseEntity = cocktailController.findCocktailList(search);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCocktails, responseEntity.getBody());
    }

    @Test
    void findCocktailById() throws Exception {
        // Given
        String cocktailId = UUID.randomUUID().toString();
        UUID expectedCocktailUUID = UUID.randomUUID();
        CocktailDto expectedCocktail = new CocktailDto(expectedCocktailUUID.toString(),"1233o", "Gin Fizz", "500ml", "prepare",
                "image-url", Set.of("cola","jin"));
        when(cocktailService.getCocktailById(UUID.fromString(cocktailId))).thenReturn(expectedCocktail);

        CocktailController cocktailController = new CocktailController(cocktailDBClient, cocktailService);

        // When
        ResponseEntity<CocktailDto> responseEntity = cocktailController.findCocktailById(cocktailId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedCocktail, responseEntity.getBody());
    }
}