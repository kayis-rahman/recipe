package com.books.recipes.data;

import com.books.recipes.entities.Recipe;

import static com.books.recipes.data.MockDataIngredients.getIngredients;

public class MockDataRecipe {

    public static Recipe getNewRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setId(1L);
        newRecipe.setName("Roasted Asparagus");
        newRecipe.setVegetarian(true);
        newRecipe.setInstructions("Preheat oven to 425°F.");
        newRecipe.setNumberOfServings(2);
        newRecipe.setIngredients(getIngredients());
        return newRecipe;
    }

    public static Recipe recipeForUpdate(Recipe storedRecipe) {
        String updateFormat = "%sUpdated";
        storedRecipe.getIngredients().addAll(storedRecipe.getIngredients());

        storedRecipe.setName(String.format(updateFormat, storedRecipe.getName()));
        storedRecipe.setInstructions(String.format(updateFormat, storedRecipe.getInstructions()));
        storedRecipe.setVegetarian(!storedRecipe.getVegetarian());
        storedRecipe.setNumberOfServings(storedRecipe.getNumberOfServings() + 1);
        storedRecipe.setIngredients(storedRecipe.getIngredients());
        return storedRecipe;
    }


}
