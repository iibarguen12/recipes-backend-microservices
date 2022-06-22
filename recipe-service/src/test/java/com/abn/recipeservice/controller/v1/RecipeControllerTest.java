package com.abn.recipeservice.controller.v1;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.domain.Ingredient;
import com.abn.recipeservice.domain.Recipe;
import com.abn.recipeservice.repo.FoodCategoryRepo;
import com.abn.recipeservice.repo.IngredientRepo;
import com.abn.recipeservice.repo.RecipeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FoodCategoryRepo foodCategoryRepo;
    @Autowired
    private IngredientRepo ingredientRepo;
    @Autowired
    private RecipeRepo recipeRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private static List<FoodCategory> foodCategoryList;

    private static List<Ingredient> ingredientList;

    private static Recipe recipe1;
    private static Recipe recipe2;
    private static Recipe recipe3;
    private static List<Recipe> recipeList;

    @BeforeAll
    static void setup(){
        //Setup of the food categories
        FoodCategory foodCategory1 = FoodCategory.builder().id(null).name("TEST-Fruit").build();
        FoodCategory foodCategory2 = FoodCategory.builder().id(null).name("TEST-Meat").build();
        FoodCategory foodCategory3 = FoodCategory.builder().id(null).name("TEST-Vegetarian").build();
        foodCategoryList = Arrays.asList(foodCategory1, foodCategory2, foodCategory3);
        //Setup of the ingredients
        Ingredient ingredient1 = Ingredient.builder().id(null).name("TEST-Sugar").build();
        Ingredient ingredient2 = Ingredient.builder().id(null).name("TEST-Chicken").build();
        Ingredient ingredient3 = Ingredient.builder().id(null).name("TEST-Tomatoes").build();
        ingredientList = Arrays.asList(ingredient1, ingredient2, ingredient3);
    }

    static void setupRecipes(){
        //Setup of the recipes
        recipe1 = Recipe.builder().id(null).username("TEST-abn_user").title("TEST-Roasted chicken")
                .foodCategory(foodCategoryList.get(1)).ingredients(ingredientList).build();
        recipe2 = Recipe.builder().id(null).username("TEST-abn_user").title("TEST-Grilled chicken")
                .foodCategory(foodCategoryList.get(1)).ingredients(ingredientList).build();
        recipe3 = Recipe.builder().id(null).username("TEST-abn_admin").title("TEST-Caesar salad")
                .foodCategory(foodCategoryList.get(2)).ingredients(ingredientList).build();
        recipeList = Arrays.asList(recipe1, recipe2, recipe3);
    }

    @Test
    @Order(1)
    void getRecipeNotFound() throws Exception {
        // given - precondition or setup
        // without saving any object

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/recipe-management/v1/recipes/{id}",
                        -999)
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(2)
    void getRecipe() throws Exception {
        // given - precondition or setup
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();
        recipe1 = recipeRepo.save(recipe1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/recipe-management/v1/recipes/{id}",
                        recipe1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(recipe1.getTitle())));
    }

    @Test
    @Order(3)
    void getRecipes() throws Exception {
        // given - precondition or setup
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();
        recipeList = recipeRepo.saveAll(recipeList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/recipe-management/v1/recipes")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",Matchers.is(recipeList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",Matchers.is(recipeList.get(0).getTitle())));
    }

    @Test
    @Order(4)
    void getRecipesByTitle() throws Exception {
        // given - precondition or setup
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();
        recipeList = recipeRepo.saveAll(recipeList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/recipe-management/v1/recipes")
                        .param("title", "chicken")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",Matchers.is(recipeList.get(0).getTitle())));
    }

    @Test
    @Order(5)
    void getRecipesByUsername() throws Exception {
        // given - precondition or setup
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();
        recipeList = recipeRepo.saveAll(recipeList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/recipe-management/v1/recipes")
                        .param("username", "TEST-abn_admin")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",Matchers.is(recipeList.get(2).getTitle())));
    }

    @Test
    @Order(6)
    void getRecipesByFoodCategory() throws Exception {
        // given - precondition or setup
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();
        recipeList = recipeRepo.saveAll(recipeList);


        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/recipe-management/v1/recipes")
                        .param("foodCategory", "TEST-Vegetarian")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",Matchers.is(recipeList.get(2).getTitle())));
    }

    @Test
    @Order(7)
    void saveRecipe() throws Exception {
        // given - precondition or setup
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/recipe-management/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe1))
        );

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(recipe1.getTitle())));
    }

    @Test
    @Order(8)
    void deleteRecipe() throws Exception {
        // given - precondition or setup
        // using the already defined object foodCategory1
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);
        ingredientList = ingredientRepo.saveAll(ingredientList);
        setupRecipes();
        recipe1 = recipeRepo.save(recipe1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                delete("/recipe-management/v1/recipes/{id}",
                        recipe1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.is(1)));
    }
}