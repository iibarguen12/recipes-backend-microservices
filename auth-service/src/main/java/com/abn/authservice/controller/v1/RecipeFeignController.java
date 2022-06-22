package com.abn.authservice.controller.v1;

import com.abn.authservice.dto.RecipeDto;
import com.abn.authservice.exception.ResourceNotFoundException;
import com.abn.authservice.service.RecipeFeignService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/recipes")
@RequiredArgsConstructor
public class RecipeFeignController {

    @Autowired
    private final RecipeFeignService recipeFeignService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id){
        try{
            return recipeFeignService.getRecipe(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<RecipeDto>> getRecipes(){
        return recipeFeignService.getRecipes();
    }

    @GetMapping(value = "", params = "title")
    public ResponseEntity<List<RecipeDto>> getRecipesByTitle(@RequestParam String title){
        return recipeFeignService.getRecipesByTitle(title);
    }

    @GetMapping(value = "", params = "username")
    public ResponseEntity<List<RecipeDto>> getRecipesByUsername(@RequestParam String username){
        return recipeFeignService.getRecipesByUsername(username);
    }

    @GetMapping(value = "", params = "foodCategoryDto")
    public ResponseEntity<List<RecipeDto>> getRecipesByFoodCategory(@RequestParam String foodCategory){
        return recipeFeignService.getRecipesByFoodCategory(foodCategory);
    }

    @PostMapping("")
    public ResponseEntity<RecipeDto> saveRecipe(@RequestBody RecipeDto recipe){
        return recipeFeignService.saveRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRecipe(@PathVariable Long id){
        try{
            return recipeFeignService.deleteRecipe(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }
}
