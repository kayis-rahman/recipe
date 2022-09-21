package com.books.recipes.data;

import com.books.recipes.entities.Ingredient;

import java.util.ArrayList;

public class MockDataIngredients {

    public static ArrayList<Ingredient> getIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(getIngredient());
        return ingredients;
    }

    public static Ingredient getIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Salt");
        ingredient.setType("Taste");
        ingredient.setQuantity(2);
        return ingredient;
    }
}
