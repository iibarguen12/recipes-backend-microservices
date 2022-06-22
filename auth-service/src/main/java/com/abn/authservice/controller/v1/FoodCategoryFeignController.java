package com.abn.authservice.controller.v1;

import com.abn.authservice.dto.FoodCategoryDto;
import com.abn.authservice.exception.ResourceNotFoundException;
import com.abn.authservice.service.FoodCategoryFeignService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/food-categories")
@RequiredArgsConstructor
public class FoodCategoryFeignController {

    @Autowired
    private final FoodCategoryFeignService foodCategoryFeignService;

    @GetMapping("/{id}")
    public ResponseEntity<FoodCategoryDto> getFoodCategory(@PathVariable Long id) {
        try{
            return foodCategoryFeignService.getFoodCategory(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<FoodCategoryDto>> getFoodCategories(){
        return foodCategoryFeignService.getFoodCategories();
    }

    @PostMapping("")
    public ResponseEntity<FoodCategoryDto> saveFoodCategory(@RequestBody FoodCategoryDto foodCategory){
        return foodCategoryFeignService.saveFoodCategory(foodCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteFoodCategory(@PathVariable Long id){
        try{
            return foodCategoryFeignService.deleteFoodCategory(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }
}
