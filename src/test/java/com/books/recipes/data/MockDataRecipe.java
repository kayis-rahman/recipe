package com.books.recipes.data;

import com.books.recipes.entities.Recipe;

import static com.books.recipes.data.MockDataIngredients.getIngredients;

public class MockDataRecipe {

    public static Recipe getNewRecipe() {
        com.books.recipes.entities.Recipe newRecipe = new com.books.recipes.entities.Recipe();
        newRecipe.setId(1L);
        newRecipe.setName("Test Recipe");
        newRecipe.setVegetarian(false);
        newRecipe.setInstructions("Instructions");
        newRecipe.setNumberOfServings(2);
        newRecipe.setIngredients(getIngredients());
        return newRecipe;
    }

}
