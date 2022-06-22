package com.abn.recipeservice.controller.v1;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.service.FoodCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food-category-management/v1")
public class FoodCategoryController {
    private final FoodCategoryService foodCategoryService;

    @GetMapping("/food-categories/{id}")
    public ResponseEntity<FoodCategory> getFoodCategory(@PathVariable Long id){
        return ResponseEntity.ok().body(foodCategoryService.findFoodCategoryById(id));
    }

    @GetMapping("/food-categories")
    public ResponseEntity<List<FoodCategory>> getFoodCategories(){
        return ResponseEntity.ok().body(foodCategoryService.getFoodCategories());
    }

    @PostMapping("/food-categories")
    public ResponseEntity<FoodCategory> saveFoodCategory(@RequestBody FoodCategory foodCategory){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/food-category-management/v1/food-categories")
                .toUriString());
        return ResponseEntity.created(uri).body(foodCategoryService.saveFoodCategory(foodCategory));
    }

    @DeleteMapping("/food-categories/{id}")
    public ResponseEntity<Long> deleteFoodCategory(@PathVariable Long id){
        return ResponseEntity.ok().body(foodCategoryService.deleteFoodCategory(id));
    }
}
