package com.abn.recipeservice.repo;

import com.abn.recipeservice.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepo extends JpaRepository<FoodCategory,Long> {
    Long deleteByName(String name);
    FoodCategory findByName(String foodCategoryName);
}
