package com.abn.recipeservice.service;

import com.abn.recipeservice.domain.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient findIngredientById(Long id);
    List<Ingredient> getIngredients();
    Ingredient saveIngredient(Ingredient ingredient);
    Long deleteIngredient(Long id);
}
