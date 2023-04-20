package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.dto.ShoppingListDto;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListControllerTest {

    private ShoppingListController shoppingListController;

    @Mock
    private ShoppingListService shoppingListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingListController = new ShoppingListController(shoppingListService);
    }

    @Test
    void findShoppingListById() throws Exception {
        // given
        String id = UUID.randomUUID().toString();
        ShoppingListDto shoppingListDto = ShoppingListDto.builder()
                .id(id)
                .name("Test Shopping List")
                .cocktails(new HashSet<>())
                .ingredients(new HashSet<>())
                .build();
        when(shoppingListService.getShoppingListById(id)).thenReturn(shoppingListDto);

        // when
        ResponseEntity<ShoppingListDto> response = shoppingListController.findShoppingListById(id);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(shoppingListDto);
    }

    @Test
    void findAllShoppingList() throws Exception {
        // given
        List<ShoppingListDto> shoppingListDtoList = new ArrayList<>();
        shoppingListDtoList.add(ShoppingListDto.builder()
                .id(UUID.randomUUID().toString())
                .name("Test Shopping List 1")
                .cocktails(new HashSet<>())
                .ingredients(new HashSet<>())
                .build());
        shoppingListDtoList.add(ShoppingListDto.builder()
                .id(UUID.randomUUID().toString())
                .name("Test Shopping List 2")
                .cocktails(new HashSet<>())
                .ingredients(new HashSet<>())
                .build());
        when(shoppingListService.getAllShoppingLists()).thenReturn(shoppingListDtoList);

        // when
        ResponseEntity<List<ShoppingListDto>> response = shoppingListController.findAllShoppingList();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(shoppingListDtoList);
    }

//    @Test
//    public void testAddCocktailToShoppingList() throws Exception {
//
//        UUID expectedCocktailUUID = UUID.randomUUID();
//        CocktailDto expectedCocktail = new CocktailDto(expectedCocktailUUID.toString(),"1233o", "Gin Fizz", "500ml", "prepare",
//                "image-url", Set.of("cola","jin"));
//
//        String shoppingListId = UUID.randomUUID().toString();
//        Set<String> cocktails = Set.of("Martini");
//        ShoppingListDto shoppingListDto = new ShoppingListDto(shoppingListId,
//                "My Shopping List", Set.of(expectedCocktail),Set.of("Cola"));
//        when(shoppingListService.addCocktailToShoppingList(eq(shoppingListId), anySet())).thenReturn(shoppingListDto);
//
//        ResponseEntity<Void> response = shoppingListController.addCocktailToShoppingList(shoppingListId, "Martini");
//
//        assertEquals(201, response.getStatusCode());
//        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
//                .path("/{resourceId}")
//                .buildAndExpand(shoppingListDto.getId())
//                .toUri();
//        assertEquals(location, response.getHeaders().getLocation());
//    }
//
//    @Test
//    public void testCreateNewShoppingList() throws Exception {
//        ShoppingListDto shoppingListDto = new ShoppingListDto(null, "My Shopping List",Set.of(null),Set.of(null));
//        when(shoppingListService.create(any())).thenReturn(shoppingListDto);
//
//        ResponseEntity<Void> response = shoppingListController.createNewShoppingList(shoppingListDto);
//
//        assertEquals(201, response.getStatusCode());
//        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
//                .path("/{resourceId}")
//                .buildAndExpand(shoppingListDto.getId())
//                .toUri();
//        assertEquals(location, response.getHeaders().getLocation());
//    }
}
