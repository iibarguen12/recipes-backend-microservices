package com.abn.recipeservice.service;

import com.abn.recipeservice.domain.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    FoodCategory findFoodCategoryById(Long id);
    List<FoodCategory> getFoodCategories();
    FoodCategory saveFoodCategory(FoodCategory foodCategory);
    Long deleteFoodCategory(Long id);
}
