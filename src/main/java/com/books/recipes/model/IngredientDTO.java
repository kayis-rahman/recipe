package com.books.recipes.model;

import com.books.recipes.entities.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private Long id;

    @NotEmpty(message = "Ingredients name should not empty")
    @NotNull(message = "Ingredients name should not null")
    private String name;

    @NotNull(message = "Quantity name should not null")
    private Integer quantity;

    @NotEmpty(message = "Type should not empty")
    @NotNull(message = "Type name should not null")
    private String type;

    public IngredientDTO from(Ingredient ingredient) {
        this.setId(ingredient.getId());
        this.setName(ingredient.getName());
        this.setType(ingredient.getType());
        this.setQuantity(ingredient.getQuantity());
        return this;
    }

    public Ingredient toIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(getId());
        ingredient.setType(getType());
        ingredient.setName(getName());
        ingredient.setQuantity(getQuantity());
        return ingredient;
    }
}
