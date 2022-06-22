package com.abn.recipeservice.controller.v1;

import com.abn.recipeservice.domain.FoodCategory;
import com.abn.recipeservice.domain.Ingredient;
import com.abn.recipeservice.repo.IngredientRepo;
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
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IngredientRepo ingredientRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private static Ingredient ingredient1;
    private static Ingredient ingredient2;
    private static Ingredient ingredient3;
    private static List<Ingredient> ingredientList;

    @BeforeAll
    static void setup(){
        ingredient1 = Ingredient.builder().id(null).name("TEST-Sugar").build();
        ingredient2 = Ingredient.builder().id(null).name("TEST-Chicken").build();
        ingredient3 = Ingredient.builder().id(null).name("TEST-Tomatoes").build();
        ingredientList = Arrays.asList(ingredient1,ingredient2,ingredient3);
    }

    @Test
    @Order(1)
    void getIngredientNotFound() throws Exception {
        // given - precondition or setup
        // without saving any object

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/ingredient-management/v1/ingredients/{id}",
                        -999)
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(2)
    void getIngredient() throws Exception {
        // given - precondition or setup
        // using the already defined object ingredient1
        ingredientRepo.save(ingredient1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/ingredient-management/v1/ingredients/{id}",
                        ingredient1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(ingredient1.getName())));
    }

    @Test
    @Order(3)
    void getIngredients() throws Exception {
        // given - precondition or setup
        // using the already defined list ingredientList
        ingredientRepo.saveAll(ingredientList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/ingredient-management/v1/ingredients")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",Matchers.is(ingredientList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",Matchers.is(ingredient1.getName())));
    }

    @Test
    @Order(4)
    void saveIngredient() throws Exception {
        // given - precondition or setup
        // using the already defined object ingredient1

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/ingredient-management/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient1))
        );

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(ingredient1.getName())));
    }

    @Test
    @Order(5)
    void deleteIngredient() throws Exception {
        // given - precondition or setup
        // using the already defined object ingredient1
        ingredient1 = ingredientRepo.save(ingredient1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                delete("/ingredient-management/v1/ingredients/{id}",
                        ingredient1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.is(1)));
    }
}