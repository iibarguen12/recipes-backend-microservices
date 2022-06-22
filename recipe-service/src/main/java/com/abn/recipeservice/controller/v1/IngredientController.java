package com.abn.recipeservice.controller.v1;

import com.abn.recipeservice.domain.Ingredient;
import com.abn.recipeservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient-management/v1")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id){
        return ResponseEntity.ok().body(ingredientService.findIngredientById(id));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredients(){
        return ResponseEntity.ok().body(ingredientService.getIngredients());
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> saveIngredient(@RequestBody Ingredient ingredient){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/ingredients-management/v1/ingredients")
                .toUriString());
        return ResponseEntity.created(uri).body(ingredientService.saveIngredient(ingredient));
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Long> deleteIngredient(@PathVariable Long id){
        return ResponseEntity.ok().body(ingredientService.deleteIngredient(id));
    }
}
