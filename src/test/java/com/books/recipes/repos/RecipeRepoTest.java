package com.books.recipes.repos;

import com.books.recipes.entities.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RecipeRepoTest {

    private final Recipe recipe = getNewRecipe();
    @Autowired
    private RecipeRepo recipeRepo;

    @Test
    void testStoreRecipeSuccessfully() {

        Recipe savedRecipe = recipeRepo.save(recipe);

        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getId()).isNotNull();
        assertThat(savedRecipe.getIngredients().size()).isEqualTo(recipe.getIngredients().size());
        assertThat(savedRecipe.getName()).isEqualTo(recipe.getName());
    }

    @Test
    void testGetARecipe() {
        Recipe storedRecipe = recipeRepo.save(recipe);

        Optional<Recipe> maybeRecipe = recipeRepo.findById(storedRecipe.getId());

        assertThat(maybeRecipe.isEmpty()).isFalse();
        assertThat(maybeRecipe.get().getId()).isEqualTo(storedRecipe.getId());
        assertThat(maybeRecipe.get().getName()).isEqualTo(storedRecipe.getName());
    }

    @Test
    void testGetAllRecipe() {
        Recipe storedRecipe = recipeRepo.save(recipe);

        List<Recipe> recipes = recipeRepo.findAll();

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe).isNotNull();
        assertThat(foundRecipe.getName()).isEqualTo(storedRecipe.getName());
        assertThat(foundRecipe.getInstructions()).isEqualTo(storedRecipe.getInstructions());
        assertThat(foundRecipe.getIngredients().size()).isEqualTo(storedRecipe.getIngredients().size());
    }
}