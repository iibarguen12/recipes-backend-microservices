package com.abn.authservice.controller.v1;

import com.abn.authservice.dto.IngredientDto;
import com.abn.authservice.exception.ResourceNotFoundException;
import com.abn.authservice.service.IngredientFeignService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ingredients")
@RequiredArgsConstructor
public class IngredientFeignController {

    @Autowired
    private final IngredientFeignService ingredientFeignService;

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable Long id){
        try{
            return ingredientFeignService.getIngredient(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<IngredientDto>> getIngredients(){
        return ingredientFeignService.getIngredients();
    }

    @PostMapping("")
    public ResponseEntity<IngredientDto> saveIngredient(@RequestBody IngredientDto ingredient){
        return ingredientFeignService.saveIngredient(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteIngredient(@PathVariable Long id){
        try{
            return ingredientFeignService.deleteIngredient(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }
}
