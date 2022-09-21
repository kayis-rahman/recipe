package com.books.recipes.service;

import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.repos.RecipeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepo recipeRepo;
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeService(recipeRepo);
    }

    @Test
    void testSaveRecipeInfo() {
        Recipe newRecipe = getNewRecipe();
        when(recipeRepo.save(any())).thenReturn(newRecipe);

        RecipeDTO recipeDTO = new RecipeDTO().from(newRecipe);
        Recipe recipe = recipeService.add(recipeDTO);

        assertThat(recipe).isNotNull();
        assertThat(recipe.getId()).isNotNull();
        assertThat(recipe.getInstructions()).isEqualTo(recipeDTO.getInstructions());
    }

}