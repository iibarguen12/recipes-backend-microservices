package com.abn.authservice.service;

import com.abn.authservice.dto.RecipeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("${microservices.recipe.name}/recipe-management/v1")
public interface RecipeFeignService {
    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id);

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDto>> getRecipes();

    @GetMapping(value = "/recipes", params = "title")
    public ResponseEntity<List<RecipeDto>> getRecipesByTitle(@RequestParam String title);

    @GetMapping(value = "/recipes", params = "username")
    public ResponseEntity<List<RecipeDto>> getRecipesByUsername(@RequestParam String username);

    @GetMapping(value = "/recipes", params = "foodCategoryDto")
    public ResponseEntity<List<RecipeDto>> getRecipesByFoodCategory(@RequestParam String foodCategory);

    @PostMapping("/recipes")
    public ResponseEntity<RecipeDto> saveRecipe(@RequestBody RecipeDto recipe);

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Long> deleteRecipe(@PathVariable Long id);
}
