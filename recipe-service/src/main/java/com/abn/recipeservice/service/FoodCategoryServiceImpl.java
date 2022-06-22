package com.abn.recipeservice.service;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.exception.ResourceNotFoundException;
import com.abn.recipeservice.repo.FoodCategoryRepo;
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
public class FoodCategoryServiceImpl implements FoodCategoryService{

    private final FoodCategoryRepo foodCategoryRepo;

    @Override
    public FoodCategory findFoodCategoryById(Long id) {
        log.info("Fetching food category id: {}", id);
        return foodCategoryRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<FoodCategory> getFoodCategories() {
        log.info("Fetching all the food categories");
        return foodCategoryRepo.findAll();
    }

    @Override
    public FoodCategory saveFoodCategory(FoodCategory foodCategory) {
        log.info("Saving food category name: {}", foodCategory.getName());
        return foodCategoryRepo.save(foodCategory);
    }

    @Override
    public Long deleteFoodCategory(Long id) {
        log.info("Deleting food category id: {}", id);
        Optional<FoodCategory> foodCategory = foodCategoryRepo.findById(id);
        return foodCategoryRepo.deleteByName(foodCategory.orElse(null) != null ? foodCategory.orElse(null).getName() : null);
    }
}
