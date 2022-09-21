package com.books.recipes.controller;

import com.books.recipes.entities.Recipe;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/all")
    private String getAllRecipe() {
        return "Hello Wold";
    }

    @PostMapping()
    private ResponseEntity<Recipe> add(@Valid @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.add(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

}
