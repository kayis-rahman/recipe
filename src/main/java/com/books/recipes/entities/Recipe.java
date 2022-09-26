package com.books.recipes.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private Boolean vegetarian;
    private Integer numberOfServings;
    @Column(columnDefinition = "TEXT")
    private String instructions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private List<Ingredient> ingredients;

}
