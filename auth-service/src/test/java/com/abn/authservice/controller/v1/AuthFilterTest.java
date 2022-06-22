package com.abn.authservice.controller.v1;

import com.abn.authservice.domain.User;
import com.abn.authservice.repo.UserRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthFilterTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepo userRepo;

    private static User user1;

    @BeforeAll
    static void setup() {
        user1 = User.builder().name("ABN User").username("abn_user").password("user").build();
    }

    @Test
    @Order(1)
    void unauthorizedRequest() throws Exception {
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", "wrong_user")
                        .param("password", "wrong_password")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    void forbiddenRequest() throws Exception {
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/user-management/v1/users")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "abn_admin",roles = {"ADMIN"})
    void authorizedRequest() throws Exception {
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/user-management/v1/users/1")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(user1.getUsername())));
    }
}