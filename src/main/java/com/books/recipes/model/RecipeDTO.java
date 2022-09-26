package com.books.recipes.model;


import com.books.recipes.entities.Ingredient;
import com.books.recipes.entities.Recipe;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecipeDTO {

    private Long id;

    @NotEmpty(message = "Recipe name should not empty")
    @NotNull(message = "Recipe name should not null")
    private String name;

    @NotNull(message = "Vegetarian should not null, it should be either true or false")
    private Boolean vegetarian;

    @NotNull(message = "NumberOfServings name should not null")
    private Integer numberOfServings;

    @NotEmpty(message = "Instructions name should not empty")
    @NotNull(message = "Instructions name should not null")
    private String instructions;

    @NotEmpty(message = "Ingredients should not empty")
    @NotNull(message = "Ingredients should not null")
    private List<IngredientDTO> ingredients;

    private static IngredientDTO from(Ingredient ingredient) {
        return new IngredientDTO().from(ingredient);
    }

    public RecipeDTO from(Recipe recipe) {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        if (recipe.getIngredients() != null) {
            ingredientDTOList = recipe.getIngredients().stream().map(RecipeDTO::from).collect(Collectors.toList());
        }

        this.setId(recipe.getId());
        this.setName(recipe.getName());
        this.setIngredients(ingredientDTOList);
        this.setVegetarian(recipe.getVegetarian());
        this.setNumberOfServings(recipe.getNumberOfServings());
        this.setInstructions(recipe.getInstructions());
        return this;
    }

    public Recipe toRecipe() {
        List<Ingredient> ingredientList = getIngredients().stream().map(IngredientDTO::toIngredient).collect(Collectors.toList());
        Recipe recipe = new Recipe();
        recipe.setIngredients(ingredientList);
        recipe.setName(getName());
        recipe.setId(getId());
        recipe.setVegetarian(getVegetarian());
        recipe.setNumberOfServings(getNumberOfServings());
        recipe.setInstructions(getInstructions());
        return recipe;
    }

}
