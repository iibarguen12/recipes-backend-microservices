package com.abn.recipeservice.controller.v1;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.repo.FoodCategoryRepo;
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
class FoodCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FoodCategoryRepo foodCategoryRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private static FoodCategory foodCategory1;
    private static FoodCategory foodCategory2;
    private static FoodCategory foodCategory3;
    private static List<FoodCategory> foodCategoryList;

    @BeforeAll
    static void setup(){
        foodCategory1 = FoodCategory.builder().id(null).name("TEST-Fruit").build();
        foodCategory2 = FoodCategory.builder().id(null).name("TEST-Meat").build();
        foodCategory3 = FoodCategory.builder().id(null).name("TEST-Vegetarian").build();
        foodCategoryList = Arrays.asList(foodCategory1, foodCategory2, foodCategory3);
    }

    @Test
    @Order(1)
    void getFoodCategoryNotFound() throws Exception {
        // given - precondition or setup
        // without saving any object

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/food-category-management/v1/food-categories/{id}",
                        -999)
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(2)
    void getFoodCategory() throws Exception {
        // given - precondition or setup
        // using the already defined object foodCategory1
        foodCategory1 = foodCategoryRepo.save(foodCategory1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/food-category-management/v1/food-categories/{id}",
                        foodCategory1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(foodCategory1.getName())));
    }

    @Test
    @Order(3)
    void getFoodCategories() throws Exception {
        // given - precondition or setup
        // using the already defined list foodCategoryList
        foodCategoryList = foodCategoryRepo.saveAll(foodCategoryList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/food-category-management/v1/food-categories")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",Matchers.is(foodCategoryList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",Matchers.is(foodCategory1.getName())));
    }

    @Test
    @Order(4)
    void saveFoodCategory() throws Exception {
        // given - precondition or setup
        // using the already defined object foodCategory1

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/food-category-management/v1/food-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodCategory1))
        );

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(foodCategory1.getName())));

    }

    @Test
    @Order(5)
    void deleteFoodCategory() throws Exception {
        // given - precondition or setup
        // using the already defined object foodCategory1
        foodCategory1 = foodCategoryRepo.save(foodCategory1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                delete("/food-category-management/v1/food-categories/{id}",
                        foodCategory1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.is(1)));
    }
}