package com.books.recipes.helper;

import com.books.recipes.entities.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapperHelperTest {

    @Test
    void testMethodAsJsonString() {
        Ingredient ingredient = getIngredient();
        String string = MapperHelper.asJsonString(ingredient);

        Assertions.assertNotNull(string);
        Assertions.assertFalse(string.isBlank());
    }

    private static Ingredient getIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Slat");
        ingredient.setType("Taste");
        return ingredient;
    }
}