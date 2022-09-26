package com.books.recipes.repos;

import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class RecipeCustomRepoTest {

    @Autowired
    private EntityManager entityManager;

    private RecipeCustomRepo recipeRepo;

    @BeforeEach
    void setUp() {
        recipeRepo = new RecipeCustomRepo(entityManager);
    }

    @Test
    void testGetAllRecipeWithAllFilter() throws IOException {

        createMockData();

        Optional<String> instruction = Optional.of("oven");
        Optional<Boolean> vegetarian = Optional.of(true);
        Optional<Integer> numOfServings = Optional.of(2);
        Optional<String> incRecipe = Optional.of("Olive oil");
        Optional<String> excRecipe = Optional.of("Salmon");
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getVegetarian()).isEqualTo(true);
        assertThat(foundRecipe.getNumberOfServings()).isEqualTo(numOfServings.get());
        assertThat(foundRecipe.getInstructions()).contains(instruction.get());
        assertThat(foundRecipe.getIngredients()).anyMatch(ingredient -> ingredient.getName().contains(incRecipe.get()));
        assertThat(foundRecipe.getIngredients()).noneMatch(ingredient -> ingredient.getName().contains(excRecipe.get()));
    }

    @Test
    void testGetAllRecipeWithFilterInstructionVegetarianNumOfServingsIncRecipe() throws IOException {

        createMockData();


        Optional<String> instruction = Optional.of("oven");
        Optional<Boolean> vegetarian = Optional.of(true);
        Optional<Integer> numOfServings = Optional.of(2);
        Optional<String> incRecipe = Optional.of("Olive oil");
        Optional<String> excRecipe = Optional.empty();
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getVegetarian()).isEqualTo(true);
        assertThat(foundRecipe.getNumberOfServings()).isEqualTo(numOfServings.get());
        assertThat(foundRecipe.getInstructions()).contains(instruction.get());
        assertThat(foundRecipe.getIngredients()).anyMatch(ingredient -> ingredient.getName().contains(incRecipe.get()));
    }

    @Test
    void testGetAllRecipeWithFilterInstructionVegetarianNumOfServings() throws IOException {

        createMockData();


        Optional<String> instruction = Optional.of("oven");
        Optional<Boolean> vegetarian = Optional.of(true);
        Optional<Integer> numOfServings = Optional.of(2);
        Optional<String> incRecipe = Optional.empty();
        Optional<String> excRecipe = Optional.empty();
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getNumberOfServings()).isEqualTo(numOfServings.get());
        assertThat(foundRecipe.getVegetarian()).isEqualTo(true);
        assertThat(foundRecipe.getInstructions()).contains(instruction.get());
    }

    @Test
    void testGetAllRecipeWithFilterInstructionVegetarian() throws IOException {

        createMockData();

        Optional<String> instruction = Optional.of("oven");
        Optional<Boolean> vegetarian = Optional.of(true);
        Optional<Integer> numOfServings = Optional.empty();
        Optional<String> incRecipe = Optional.empty();
        Optional<String> excRecipe = Optional.empty();
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getVegetarian()).isEqualTo(true);
        assertThat(foundRecipe.getInstructions()).contains(instruction.get());
    }


    @Test
    void testGetAllRecipeWithFilterIncRecipeExcRecipe() throws IOException {

        createMockData();


        Optional<String> instruction = Optional.empty();
        Optional<Boolean> vegetarian = Optional.empty();
        Optional<Integer> numOfServings = Optional.empty();
        Optional<String> incRecipe = Optional.of("Olive oil");
        Optional<String> excRecipe = Optional.of("Salmon");
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getIngredients()).anyMatch(ingredient -> ingredient.getName().contains(incRecipe.get()));
        assertThat(foundRecipe.getIngredients()).noneMatch(ingredient -> ingredient.getName().contains(excRecipe.get()));
    }

    @Test
    void testGetAllRecipeWithFilterExcRecipe() throws IOException {

        createMockData();

        Optional<String> instruction = Optional.empty();
        Optional<Boolean> vegetarian = Optional.empty();
        Optional<Integer> numOfServings = Optional.empty();
        Optional<String> incRecipe = Optional.empty();
        Optional<String> excRecipe = Optional.of("Salmon");
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(2);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getIngredients()).noneMatch(ingredient -> ingredient.getName().contains(excRecipe.get()));
    }

    @Test
    void testGetAllRecipeWithFilterNumOfServings() throws IOException {

        createMockData();

        Optional<String> instruction = Optional.empty();
        Optional<Boolean> vegetarian = Optional.empty();
        Optional<Integer> numOfServings = Optional.of(2);
        Optional<String> incRecipe = Optional.empty();
        Optional<String> excRecipe = Optional.empty();
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getNumberOfServings()).isEqualTo(numOfServings.get());
    }

    @Test
    void testGetAllRecipeWithFilterVegetarian() throws IOException {

        createMockData();

        Optional<String> instruction = Optional.empty();
        Optional<Boolean> vegetarian = Optional.of(true);
        Optional<Integer> numOfServings = Optional.empty();
        Optional<String> incRecipe = Optional.empty();
        Optional<String> excRecipe = Optional.empty();
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);
        Recipe foundRecipe = recipes.get(0);
        assertThat(foundRecipe.getVegetarian()).isEqualTo(true);
    }

    @Test
    void testGetAllRecipeWithFilterInstruction() throws IOException {

        createMockData();


        Optional<String> instruction = Optional.of("oven");
        Optional<Boolean> vegetarian = Optional.empty();
        Optional<Integer> numOfServings = Optional.empty();
        Optional<String> incRecipe = Optional.empty();
        Optional<String> excRecipe = Optional.empty();
        List<Recipe> recipes = recipeRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe);

        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(2);
        assertThat(recipes.get(0).getInstructions()).contains(instruction.get());
        assertThat(recipes.get(1).getInstructions()).contains(instruction.get());
    }

    private void createMockData() throws IOException {
        File file = new ClassPathResource("recipe.json").getFile();
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        RecipeDTO[] recipeDTOS = new ObjectMapper().readValue(jsonString, RecipeDTO[].class);
        Arrays.stream(recipeDTOS).forEach(recipeDTO -> {
            recipeRepo.save(recipeDTO.toRecipe());
        });
    }
}