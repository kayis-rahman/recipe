package com.books.recipes.integration;


import com.books.recipes.entities.Recipe;
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

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddRecipe() {
        Recipe recipe = getNewRecipe();

        ResponseEntity<Recipe> response = restTemplate.postForEntity("/recipe", recipe, Recipe.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Recipe body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getName()).isEqualTo(recipe.getName());
    }
}
