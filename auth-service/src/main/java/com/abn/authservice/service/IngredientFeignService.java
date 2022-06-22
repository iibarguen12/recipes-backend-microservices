package com.abn.authservice.service;

import com.abn.authservice.dto.IngredientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("${microservices.recipe.name}/ingredient-management/v1")
public interface IngredientFeignService {
    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable Long id);

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDto>> getIngredients();

    @PostMapping("/ingredients")
    public ResponseEntity<IngredientDto> saveIngredient(@RequestBody IngredientDto ingredient);

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Long> deleteIngredient(@PathVariable Long id);
}
