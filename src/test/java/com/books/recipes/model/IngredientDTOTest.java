package com.books.recipes.model;

import com.books.recipes.entities.Ingredient;
import org.junit.jupiter.api.Test;

import static com.books.recipes.data.MockDataIngredients.getIngredient;
import static org.assertj.core.api.Assertions.assertThat;

class IngredientDTOTest {

    Ingredient ingredient = getIngredient();

    @Test
    void testModelFromIngredient() {
        IngredientDTO ingredientDTO = new IngredientDTO().from(ingredient);

        assertThat(ingredientDTO).isNotNull();
        assertThat(ingredientDTO.getId()).isEqualTo(ingredient.getId());
        assertThat(ingredientDTO.getName()).isEqualTo(ingredient.getName());
        assertThat(ingredientDTO.getQuantity()).isEqualTo(ingredient.getQuantity());
        assertThat(ingredientDTO.getType()).isEqualTo(ingredient.getType());
    }

    @Test
    void testModelToIngredient() {
        IngredientDTO ingredientDTO = new IngredientDTO().from(ingredient);
        Ingredient ingredientFromModel = ingredientDTO.toIngredient();

        assertThat(ingredientFromModel).isNotNull();
        assertThat(ingredientFromModel.getId()).isEqualTo(ingredientDTO.getId());
        assertThat(ingredientFromModel.getName()).isEqualTo(ingredientDTO.getName());
        assertThat(ingredientFromModel.getQuantity()).isEqualTo(ingredientDTO.getQuantity());
        assertThat(ingredientFromModel.getType()).isEqualTo(ingredientDTO.getType());
    }
}