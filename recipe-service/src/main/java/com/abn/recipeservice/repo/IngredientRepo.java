package com.abn.recipeservice.repo;

import com.abn.recipeservice.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepo extends JpaRepository<Ingredient,Long> {
    Long deleteByName(String name);
}
