package com.books.recipes.repos;

import com.books.recipes.entities.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
    @Autowired
    private TestEntityManager testEntityManager;

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

        recipeRepo.save(recipe);
        Optional<Recipe> maybeRecipe = recipeRepo.findById(recipe.getId());

        assertThat(maybeRecipe.isEmpty()).isFalse();
        assertThat(maybeRecipe.get().getId()).isEqualTo(recipe.getId());
        assertThat(maybeRecipe.get().getName()).isEqualTo(recipe.getName());
    }

    @Test
    void testGetAllRecipe() {
        recipeRepo.save(recipe);

        List<Recipe> recipes = recipeRepo.findAll();

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe).isNotNull();
        assertThat(foundRecipe.getName()).isEqualTo(recipe.getName());
        assertThat(foundRecipe.getInstructions()).isEqualTo(recipe.getInstructions());
        assertThat(foundRecipe.getIngredients().size()).isEqualTo(recipe.getIngredients().size());
    }

}