package com.abn.recipeservice.service;

import com.abn.recipeservice.domain.Ingredient;
import com.abn.recipeservice.exception.ResourceNotFoundException;
import com.abn.recipeservice.repo.IngredientRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepo ingredientRepo;

    @Override
    public Ingredient findIngredientById(Long id) {
        log.info("Fetching food category id: {}", id);
        return ingredientRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Ingredient> getIngredients() {
        log.info("Fetching all the ingredients");
        return ingredientRepo.findAll();
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        log.info("Saving ingredient name: {}", ingredient.getName());
        return ingredientRepo.save(ingredient);
    }

    @Override
    public Long deleteIngredient(Long id) {
        log.info("Deleting ingredient id: {}", id);
        Optional<Ingredient> ingredient = ingredientRepo.findById(id);
        return ingredientRepo.deleteByName(ingredient.orElse(null) != null ? ingredient.orElse(null).getName() : null);
    }
}
