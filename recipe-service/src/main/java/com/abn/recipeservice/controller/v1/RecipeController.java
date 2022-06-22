package com.abn.recipeservice.controller.v1;

import com.abn.recipeservice.domain.Recipe;
import com.abn.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe-management/v1")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id){
        return ResponseEntity.ok().body(recipeService.findRecipeById(id));
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getRecipes(){
        return ResponseEntity.ok().body(recipeService.getRecipes());
    }

    @GetMapping(value = "/recipes", params = "title")
    public ResponseEntity<List<Recipe>> getRecipesByTitle(@RequestParam String title){
        return ResponseEntity.ok().body(recipeService.findRecipesByTitleContaining(title));
    }

    @GetMapping(value = "/recipes", params = "username")
    public ResponseEntity<List<Recipe>> getRecipesByUsername(@RequestParam String username){
        return ResponseEntity.ok().body(recipeService.findRecipesByUsername(username));
    }

    @GetMapping(value = "/recipes", params = "foodCategory")
    public ResponseEntity<List<Recipe>> getRecipesByFoodCategory(@RequestParam String foodCategory){
        return ResponseEntity.ok().body(recipeService.findRecipesByFoodCategory(foodCategory));
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe recipe){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/recipes-management/v1/recipes")
                .toUriString());
        return ResponseEntity.created(uri).body(recipeService.saveRecipe(recipe));
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Long> deleteRecipe(@PathVariable Long id){
        return ResponseEntity.ok().body(recipeService.deleteRecipe(id));
    }

}
