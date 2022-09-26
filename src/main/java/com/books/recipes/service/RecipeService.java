package com.books.recipes.service;

import com.books.recipes.entities.Recipe;
import com.books.recipes.exception.ResourceAlreadyPresent;
import com.books.recipes.exception.ResourceNotFound;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.repos.RecipeCustomRepo;
import com.books.recipes.repos.RecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeCustomRepo recipeCustomRepo;
    private final RecipeRepo recipeRepo;

    public Recipe add(RecipeDTO recipeDTO) {
        if (recipeDTO.getId() != null) {
            Optional<Recipe> recipe = recipeRepo.findById(recipeDTO.getId());
            if (recipe.isPresent()) {
                throw new ResourceAlreadyPresent("Recipe");
            }
        }
        return recipeRepo.save(recipeDTO.toRecipe());
    }

    public List<RecipeDTO> getAll(Optional<String> instruction, Optional<Boolean> vegetarian, Optional<Integer> numOfServings, Optional<String> incRecipe, Optional<String> excRecipe) {
        return recipeCustomRepo.findAllWithFilter(instruction, vegetarian, numOfServings, incRecipe, excRecipe).stream().map(recipe -> new RecipeDTO().from(recipe)).collect(Collectors.toList());
    }

    public RecipeDTO getOne(Long recipeId) {
        Optional<Recipe> maybeRecipe = recipeRepo.findById(recipeId);
        if (maybeRecipe.isPresent()) {
            return new RecipeDTO().from(maybeRecipe.get());
        } else {
            throw new ResourceNotFound("Recipe");
        }
    }

    public void delete(Long recipeId) {
        recipeRepo.deleteById(recipeId);
    }

    public RecipeDTO update(RecipeDTO recipeDTO) {
        Optional<Recipe> maybeRecipe = recipeRepo.findById(recipeDTO.getId());
        if (maybeRecipe.isPresent()) {
            Recipe recipe = maybeRecipe.get();
            recipe.setName(recipeDTO.getName());
            recipe.setInstructions(recipeDTO.getInstructions());
            recipe.setVegetarian(recipeDTO.getVegetarian());
            recipe.setNumberOfServings(recipeDTO.getNumberOfServings());
            recipeRepo.save(recipe);
            return recipeDTO;
        } else {
            throw new ResourceNotFound("Recipe");
        }
    }
}
