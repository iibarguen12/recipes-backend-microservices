package com.abn.recipeservice.service;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.domain.Recipe;
import com.abn.recipeservice.exception.ResourceNotFoundException;
import com.abn.recipeservice.repo.FoodCategoryRepo;
import com.abn.recipeservice.repo.RecipeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepo recipeRepo;
    private final FoodCategoryRepo foodCategoryRepo;
    @Override
    public Recipe findRecipeById(Long id) {
        log.info("Fetching recipe id: {}", id);
        return recipeRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Recipe> getRecipes() {
        log.info("Fetching all the recipes");
        return recipeRepo.findAll();
    }

    @Override
    public List<Recipe> findRecipesByTitleContaining(String title) {
        log.info("Fetching all the recipes containing title: {}", title);
        return recipeRepo.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Recipe> findRecipesByUsername(String username) {
        log.info("Fetching all the recipes for user: {}", username);
        return recipeRepo.findByUsername(username);
    }

    @Override
    public List<Recipe> findRecipesByFoodCategory(String foodCategoryName) {
        log.info("Fetching all the recipes for food category: {}", foodCategoryName);
        FoodCategory foodCategory = foodCategoryRepo.findByName(foodCategoryName);
        return recipeRepo.findByFoodCategory(foodCategory);
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        log.info("Saving recipe name: {}", recipe.getTitle());
        return recipeRepo.save(recipe);
    }

    @Override
    public Long deleteRecipe(Long id) {
        log.info("Deleting recipe id: {}", id);
        Optional<Recipe> recipe = recipeRepo.findById(id);
        return recipeRepo.deleteByTitle(recipe.orElse(null) != null ? recipe.orElse(null).getTitle() : null);
    }
}
