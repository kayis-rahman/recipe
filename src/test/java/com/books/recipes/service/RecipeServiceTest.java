package com.books.recipes.service;

import com.books.recipes.entities.Recipe;
import com.books.recipes.exception.ResourceAlreadyPresent;
import com.books.recipes.exception.ResourceNotFound;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.repos.RecipeCustomRepo;
import com.books.recipes.repos.RecipeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static com.books.recipes.data.MockDataRecipe.recipeForUpdate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepo recipeRepo;
    @Mock
    private RecipeCustomRepo recipeCustomRepo;
    private RecipeService recipeService;
    private Recipe newRecipe;

    @BeforeEach
    void setUp() {
        newRecipe = getNewRecipe();
        recipeService = new RecipeService(recipeCustomRepo, recipeRepo);
    }

    @Test
    void testUpdateRecipe() {
        Recipe forUpdate = recipeForUpdate(getNewRecipe());
        RecipeDTO recipeDTOForUpdate = new RecipeDTO().from(forUpdate);
        when(recipeRepo.save(any())).thenReturn(forUpdate);
        when(recipeRepo.findById(anyLong())).thenReturn(Optional.of(forUpdate));

        RecipeDTO updated = recipeService.update(recipeDTOForUpdate);

        assertThat(updated).isNotNull();
        assertThat(updated.getNumberOfServings()).isEqualTo(recipeDTOForUpdate.getNumberOfServings());
        assertThat(updated.getName()).isEqualTo(recipeDTOForUpdate.getName());
        assertThat(updated.getInstructions()).isEqualTo(recipeDTOForUpdate.getInstructions());
        assertThat(updated.getNumberOfServings()).isEqualTo(recipeDTOForUpdate.getNumberOfServings());
        assertThat(updated.getIngredients().size()).isEqualTo(recipeDTOForUpdate.getIngredients().size());
    }

    @Test
    void testUpdateRecipeExpectException() {
        Recipe forUpdate = recipeForUpdate(getNewRecipe());
        RecipeDTO recipeDTOForUpdate = new RecipeDTO().from(forUpdate);
        when(recipeRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.update(recipeDTOForUpdate)).isInstanceOf(ResourceNotFound.class);

        verify(recipeRepo, never()).save(any());
    }

    @Test
    void testDeleteRecipe() {
        recipeService.delete(newRecipe.getId());
        verify(recipeRepo, atLeast(1)).deleteById(anyLong());
    }

    @Test
    void testGetOneRecipe() {
        when(recipeRepo.findById(anyLong())).thenReturn(Optional.of(newRecipe));

        RecipeDTO recipe = recipeService.getOne(newRecipe.getId());

        assertThat(recipe).isNotNull();
        assertThat(recipe.getId()).isNotNull();
        assertThat(recipe.getInstructions()).isEqualTo(newRecipe.getInstructions());
    }

    @Test
    void testGetOneRecipeExpectException() {
        when(recipeRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.getOne(newRecipe.getId())).isInstanceOf(ResourceNotFound.class);
    }

    @Test
    void testSaveRecipeInfo() {
        when(recipeRepo.save(any())).thenReturn(newRecipe);

        RecipeDTO recipeDTO = new RecipeDTO().from(newRecipe);
        Recipe recipe = recipeService.add(recipeDTO);

        assertThat(recipe).isNotNull();
        assertThat(recipe.getId()).isNotNull();
        assertThat(recipe.getInstructions()).isEqualTo(recipeDTO.getInstructions());
    }

    @Test
    void testAlreadyExistRecipe() {
        when(recipeRepo.findById(any())).thenReturn(Optional.of(newRecipe));

        RecipeDTO recipeDTO = new RecipeDTO().from(newRecipe);

        assertThatThrownBy(() -> recipeService.add(recipeDTO)).isInstanceOf(ResourceAlreadyPresent.class);

        verify(recipeRepo, never()).save(any());
    }

    @Test
    void testGetAllRecipes() {
        recipeService.getAll(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        verify(recipeRepo, atMostOnce()).findAll();
    }
}