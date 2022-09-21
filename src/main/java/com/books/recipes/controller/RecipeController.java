package com.books.recipes.controller;

import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @DeleteMapping("/{recipeId}")
    private ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.delete(recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{recipeId}")
    private RecipeDTO getOneRecipe(@PathVariable Long recipeId) {
        return recipeService.getOne(recipeId);
    }

    @GetMapping()
    private List<RecipeDTO> getAllRecipe() {
        return recipeService.getAll();
    }

    @PostMapping()
    private ResponseEntity<Recipe> add(@Valid @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.add(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

}
