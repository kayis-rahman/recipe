package com.books.recipes.integration;


import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RecipeIntegrationTest {

    private final Recipe recipe = getNewRecipe();
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate.delete("/recipe/{recipeId}", recipe.getId());
    }

    @Test
    void testAddRecipe() {

        ResponseEntity<Recipe> response = createRecipeWithAssertion();

        Recipe body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getName()).isEqualTo(recipe.getName());
    }

    @Test
    void testGetAllRecipe() {

        createRecipeWithAssertion();

        ResponseEntity<RecipeDTO[]> response = restTemplate.getForEntity("/recipe", RecipeDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RecipeDTO[] body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.length).isGreaterThan(0);
    }

    @Test
    void testDeleteRecipe() {
        createRecipeWithAssertion();

        restTemplate.delete("/recipe/{recipeId}", recipe.getId());

        ResponseEntity<RecipeDTO> getEntity = restTemplate.getForEntity("/recipe/{recipeId}", RecipeDTO.class, recipe.getId());
        assertThat(getEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<Recipe> createRecipeWithAssertion() {
        ResponseEntity<Recipe> insertRecipe = restTemplate.postForEntity("/recipe", recipe, Recipe.class);
        assertThat(insertRecipe.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return insertRecipe;
    }
}
