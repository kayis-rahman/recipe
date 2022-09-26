package com.books.recipes;

import com.books.recipes.controller.RecipeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecipeApplicationTests {

    @Autowired
    private RecipeController recipeController;

    @Test
    void contextLoads() {
        assertThat(recipeController).isNotNull();
    }

    @Test
    public void applicationContextTest() {
        RecipesApplication.main(new String[]{});
    }

}
