package com.books.recipes.controller;

import com.books.recipes.entities.Recipe;
import com.books.recipes.exception.ResourceAlreadyPresent;
import com.books.recipes.exception.ResourceNotFound;
import com.books.recipes.helper.MapperHelper;
import com.books.recipes.model.RecipeDTO;
import com.books.recipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.books.recipes.data.MockDataRecipe.getNewRecipe;
import static com.books.recipes.data.MockDataRecipe.recipeForUpdate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    RecipeDTO recipeDTO = new RecipeDTO().from(getNewRecipe());
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeService recipeService;

    @Test
    void updateRecipe() throws Exception {
        Recipe recipeForUpdate = recipeForUpdate(getNewRecipe());

        RecipeDTO updatedRecipeDto = new RecipeDTO().from(recipeForUpdate);

        when(recipeService.update(any())).thenReturn(updatedRecipeDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MapperHelper.asJsonString(updatedRecipeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instructions").value(updatedRecipeDto.getInstructions()))
                .andExpect(jsonPath("$.numberOfServings").value(updatedRecipeDto.getNumberOfServings()))
                .andExpect(jsonPath("$.vegetarian").value(updatedRecipeDto.getVegetarian()))
                .andExpect(jsonPath("$.name").value(updatedRecipeDto.getName()));
    }

    @Test
    void deleteARecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/{recipeId}", recipeDTO.getId()))
                .andExpect(status().isNoContent());

        verify(recipeService, atLeast(1)).delete(anyLong());
    }

    @Test
    void getOneRecipe() throws Exception {
        when(recipeService.getOne(anyLong())).thenReturn(recipeDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{recipeId}", recipeDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(recipeDTO.getName()))
                .andExpect(jsonPath("$.instructions").value(recipeDTO.getInstructions()));
    }

    @Test
    void getOneRecipeReturnException() throws Exception {
        when(recipeService.getOne(anyLong())).thenThrow(ResourceNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{recipeId}", recipeDTO.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Record Not Found"))
                .andDo(print());

    }

    @Test
    void getAllRecipes() throws Exception {
        when(recipeService.getAll()).thenReturn(List.of(recipeDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(recipeDTO.getName()))
                .andExpect(jsonPath("$.[0].instructions").value(recipeDTO.getInstructions()))
                .andDo(print());
    }

    @Test
    void recipeCreationSuccess() throws Exception {

        when(recipeService.add(any())).thenReturn(recipeDTO.toRecipe());

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MapperHelper.asJsonString(recipeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(recipeDTO.getName()))
                .andExpect(jsonPath("$.instructions").value(recipeDTO.getInstructions()))
                .andDo(print());
    }


    @Test
    void recipeCreationFailureForConflict() throws Exception {

        when(recipeService.add(any())).thenThrow(new ResourceAlreadyPresent("Recipe"));

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MapperHelper.asJsonString(recipeDTO)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    void recipeInputValidations() throws Exception {
        RecipeDTO emptyModel = new RecipeDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MapperHelper.asJsonString(emptyModel)))
                .andExpect(status().isBadRequest());

        verify(recipeService, never()).add(any());
    }
}