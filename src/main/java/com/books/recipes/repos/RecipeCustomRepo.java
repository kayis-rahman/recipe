package com.books.recipes.repos;

import com.books.recipes.entities.Ingredient;
import com.books.recipes.entities.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RecipeCustomRepo {

    private final EntityManager entityManager;

    public List<Recipe> findAllWithFilter(Optional<String> instruction, Optional<Boolean> vegetarian, Optional<Integer> numOfServings, Optional<String> incRecipe, Optional<String> excRecipe) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);
        ArrayList<Predicate> whereConditions = new ArrayList<>();

        instruction.ifPresent(val -> {
            Predicate instructionsPredicate = criteriaBuilder.like(criteriaBuilder.lower(recipeRoot.get("instructions")), String.format("%%%s%%", val.toLowerCase()));
            whereConditions.add(instructionsPredicate);
        });
        vegetarian.ifPresent(val -> {
            Predicate vegetarianPredicate = criteriaBuilder.equal(recipeRoot.get("vegetarian"), val);
            whereConditions.add(vegetarianPredicate);
        });
        numOfServings.ifPresent(val -> {
            Predicate numberOfServingsPredicate = criteriaBuilder.equal(recipeRoot.get("numberOfServings"), val);
            whereConditions.add(numberOfServingsPredicate);
        });

        incRecipe.ifPresent(val -> {
            Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
            Root<Ingredient> subRoot = subQuery.from(Ingredient.class);
            subQuery.select(subRoot.get("recipe").get("id"));
            Predicate incRecipePredicate = criteriaBuilder.like(criteriaBuilder.lower(subRoot.get("name")), String.format("%%%s%%", val.toLowerCase()));
            subQuery.where(incRecipePredicate);
            whereConditions.add(criteriaBuilder.in(recipeRoot.get("id")).value(subQuery));
        });

        excRecipe.ifPresent(val -> {
            Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
            Root<Ingredient> subRoot = subQuery.from(Ingredient.class);
            subQuery.select(subRoot.get("recipe").get("id"));
            Predicate excRecipePredicate = criteriaBuilder.like(criteriaBuilder.lower(subRoot.get("name")), String.format("%%%s%%", val.toLowerCase()));
            subQuery.where(excRecipePredicate);
            whereConditions.add(criteriaBuilder.in(recipeRoot.get("id")).value(subQuery).not());
        });

        criteriaQuery.where(whereConditions.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void save(Recipe recipe) {
        entityManager.persist(recipe);
    }
}
