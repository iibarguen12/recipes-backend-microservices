package com.abn.recipeservice.repo;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe,Long> {
    List<Recipe> findByTitleContainingIgnoreCase(String title);
    List<Recipe> findByUsername(String username);
    List<Recipe> findByFoodCategory(FoodCategory foodCategory);
    Long deleteByTitle(String title);
}
