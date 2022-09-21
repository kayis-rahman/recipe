package com.books.recipes.service;

import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.repos.RecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepo recipeRepo;

    public Recipe add(RecipeDTO recipe) {
        return recipeRepo.save(recipe.toRecipe());
    }
}
