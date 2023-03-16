create table COCKTAIL_SHOPPING_LIST(

    COCKTAIL_ID UUID,
    SHOPPING_LIST_ID UUID,
    PRIMARY KEY (COCKTAIL_ID,SHOPPING_LIST_ID),
    FOREIGN KEY (COCKTAIL_ID) REFERENCES COCKTAIL(ID),
    FOREIGN KEY (SHOPPING_LIST_ID) REFERENCES SHOPPING_LIST(ID)

)