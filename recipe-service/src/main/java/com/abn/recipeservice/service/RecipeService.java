package com.abn.recipeservice.service;

import com.abn.recipeservice.domain.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe findRecipeById(Long id);
    List<Recipe> getRecipes();
    List<Recipe> findRecipesByTitleContaining(String title);
    List<Recipe> findRecipesByUsername(String username);
    List<Recipe> findRecipesByFoodCategory(String foodCategoryName);
    Recipe saveRecipe(Recipe recipe);
    Long deleteRecipe(Long id);
}
