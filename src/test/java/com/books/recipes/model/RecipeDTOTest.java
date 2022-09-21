package com.books.recipes.model;

import com.books.recipes.entities.Recipe;
import org.junit.jupiter.api.Test;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static org.assertj.core.api.Assertions.assertThat;

class RecipeDTOTest {
    Recipe recipe = getNewRecipe();

    @Test
    void testModelFromRecipe() {

        RecipeDTO recipeDTO = new RecipeDTO().from(recipe);

        assertThat(recipeDTO).isNotNull();
        assertThat(recipeDTO.getId()).isEqualTo(recipe.getId());
        assertThat(recipeDTO.getName()).isEqualTo(recipe.getName());
        assertThat(recipeDTO.getIngredients().size()).isEqualTo(recipe.getIngredients().size());
        assertThat(recipeDTO.getNumberOfServings()).isEqualTo(recipe.getNumberOfServings());
        assertThat(recipeDTO.getInstructions()).isEqualTo(recipe.getInstructions());
        assertThat(recipeDTO.getVegetarian()).isEqualTo(recipe.getVegetarian());
    }

    @Test
    void testModelToRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO().from(recipe);
        Recipe recipeFromModel = recipeDTO.toRecipe();


        assertThat(recipeFromModel).isNotNull();
        assertThat(recipeFromModel.getId()).isEqualTo(recipeDTO.getId());
        assertThat(recipeFromModel.getName()).isEqualTo(recipeDTO.getName());
        assertThat(recipeFromModel.getIngredients().size()).isEqualTo(recipeDTO.getIngredients().size());
        assertThat(recipeFromModel.getNumberOfServings()).isEqualTo(recipeDTO.getNumberOfServings());
        assertThat(recipeFromModel.getInstructions()).isEqualTo(recipeDTO.getInstructions());
        assertThat(recipeFromModel.getVegetarian()).isEqualTo(recipeDTO.getVegetarian());
    }
}