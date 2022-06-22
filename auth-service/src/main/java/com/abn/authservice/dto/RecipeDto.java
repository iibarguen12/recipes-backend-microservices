package com.abn.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {
    private Long id;
    private String username;
    private String title;
    private String instructions;
    private List<IngredientDto> ingredientDtos;
    private FoodCategoryDto foodCategoryDto;
    private Integer servingDishes;
    private Date creationDate;
    private Time cookTime;
    private Time preparationTime;


}
