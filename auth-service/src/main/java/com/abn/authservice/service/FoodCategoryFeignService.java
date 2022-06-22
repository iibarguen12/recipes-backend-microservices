package com.abn.authservice.service;

import com.abn.authservice.dto.FoodCategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient("${microservices.recipe.name}/food-category-management/v1")
public interface FoodCategoryFeignService {
    @GetMapping("/food-categories/{id}")
    public ResponseEntity<FoodCategoryDto> getFoodCategory(@PathVariable Long id);

    @GetMapping("/food-categories")
    public ResponseEntity<List<FoodCategoryDto>> getFoodCategories();

    @PostMapping("/food-categories")
    public ResponseEntity<FoodCategoryDto> saveFoodCategory(@RequestBody FoodCategoryDto foodCategory);

    @DeleteMapping("/food-categories/{id}")
    public ResponseEntity<Long> deleteFoodCategory(@PathVariable Long id);
}
