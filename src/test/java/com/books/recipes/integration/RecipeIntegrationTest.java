package com.books.recipes.integration;


import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.repos.RecipeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static com.books.recipes.data.MockDataRecipe.recipeForUpdate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RecipeIntegrationTest {

    private final Recipe recipe = getNewRecipe();
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecipeRepo recipeRepo;

    @BeforeEach
    void setUp() {
        recipeRepo.deleteAll();
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
    void testGetAllRecipeWithFilter() throws IOException {
        loadRecipeListAndInsert();

        String instruction = "oven";
        boolean vegetarian = true;
        int numOfServings = 2;
        String incRecipe = "Olive oil";
        String excRecipe = "Salmon";
        ResponseEntity<RecipeDTO[]> response = restTemplate.getForEntity(
                "/recipe?instruction={instruction}&vegetarian={vegetarian}&numOfServings={numOfServings}&incRecipe={incRecipe}&excRecipe={excRecipe}",
                RecipeDTO[].class,
                instruction, vegetarian, numOfServings, incRecipe, excRecipe
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RecipeDTO[] body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.length).isEqualTo(1);
        assertThat(body[0].getNumberOfServings()).isEqualTo(numOfServings);
        assertThat(body[0].getVegetarian()).isEqualTo(vegetarian);
        assertThat(body[0].getInstructions()).contains(instruction);
        assertThat(body[0].getIngredients()).anyMatch(ingredient -> ingredient.getName().contains(incRecipe));
        assertThat(body[0].getIngredients()).noneMatch(ingredient -> ingredient.getName().contains(excRecipe));
    }

    @Test
    void testDeleteRecipe() {
        createRecipeWithAssertion();

        restTemplate.delete("/recipe/{recipeId}", recipe.getId());

        ResponseEntity<RecipeDTO> getEntity = restTemplate.getForEntity("/recipe/{recipeId}", RecipeDTO.class, recipe.getId());
        assertThat(getEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void testGetARecipeById() {
        Recipe storedRecipe = createRecipeWithAssertion().getBody();
        assertThat(storedRecipe).isNotNull();

        ResponseEntity<RecipeDTO> getEntity = restTemplate.getForEntity("/recipe/{recipeId}", RecipeDTO.class, storedRecipe.getId());
        assertThat(getEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        RecipeDTO body = getEntity.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(storedRecipe.getId());
        assertThat(body.getName()).isEqualTo(storedRecipe.getName());
        assertThat(body.getIngredients().size()).isEqualTo(storedRecipe.getIngredients().size());
    }

    @Test
    void testUpdateRecipe() {
        Recipe storedRecipe = createRecipeWithAssertion().getBody();
        assertThat(storedRecipe).isNotNull();

        recipeForUpdate(storedRecipe);

        HttpEntity<RecipeDTO> httpEntity = new HttpEntity<>(new RecipeDTO().from(storedRecipe));
        ResponseEntity<RecipeDTO> response = restTemplate.exchange("/recipe", HttpMethod.PUT, httpEntity, RecipeDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        RecipeDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo(storedRecipe.getName());
        assertThat(body.getInstructions()).isEqualTo(storedRecipe.getInstructions());
        assertThat(body.getVegetarian()).isEqualTo(storedRecipe.getVegetarian());
        assertThat(body.getNumberOfServings()).isEqualTo(storedRecipe.getNumberOfServings());
        assertThat(body.getIngredients().size()).isEqualTo(storedRecipe.getIngredients().size());
    }

    private ResponseEntity<Recipe> createRecipeWithAssertion() {
        ResponseEntity<Recipe> insertRecipe = restTemplate.postForEntity("/recipe", recipe, Recipe.class);
        assertThat(insertRecipe.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return insertRecipe;
    }

    private void loadRecipeListAndInsert() throws IOException {
        File file = new ClassPathResource("recipe.json").getFile();
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        RecipeDTO[] recipeDTOS = new ObjectMapper().readValue(jsonString, RecipeDTO[].class);
        Arrays.stream(recipeDTOS).forEach(recipeDTO -> {
            ResponseEntity<Recipe> insertRecipe = restTemplate.postForEntity("/recipe", recipeDTO, Recipe.class);
            assertThat(insertRecipe.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        });
    }
}
