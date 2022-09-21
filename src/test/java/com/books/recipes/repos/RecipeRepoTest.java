package com.books.recipes.repos;

import com.books.recipes.entities.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RecipeRepoTest {

    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testStoreRecipeSuccessfully() {

        Recipe recipe = getNewRecipe();

        Recipe savedRecipe = recipeRepo.save(recipe);

        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getId()).isNotNull();
        assertThat(savedRecipe.getIngredients().size()).isEqualTo(recipe.getIngredients().size());
        assertThat(savedRecipe.getName()).isEqualTo(recipe.getName());
    }

}