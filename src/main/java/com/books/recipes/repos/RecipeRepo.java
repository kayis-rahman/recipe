package com.books.recipes.repos;

import com.books.recipes.entities.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepo extends CrudRepository<Recipe, Long> {

    List<Recipe> findAll();
}
