package com.abn.userservice.controller.v1;

import com.abn.userservice.domain.Role;
import com.abn.userservice.domain.User;
import com.abn.userservice.dto.RoleToUserFormDto;
import com.abn.userservice.repo.RoleRepo;
import com.abn.userservice.repo.UserRepo;
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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    private static User user1;
    private static User user2;
    private static User user3;
    private static List<User> userList;
    private static Role role1;
    private static Role role2;
    private static List<Role> roleList;

    @BeforeAll
    static void setup() {
        user1 = User.builder().name("Test User 1").username("test_user_1").password("user1").build();
        user2 = User.builder().name("Test User 2").username("test_user_2").password("user2").build();
        user3 = User.builder().name("Test User 3").username("test_user_3").password("user3").build();
        userList = Arrays.asList(user1,user2,user3);
        role1 = Role.builder().name("ROLE_STAFF").build();
        role2 = Role.builder().name("ROLE_GUEST").build();
        roleList = Arrays.asList(role1,role2);
    }

    @Test
    @Order(1)
    void getUser() throws Exception {
        // given - precondition or setup
        user1 = userRepo.save(user1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/user-management/v1/users/{id}",
                        user1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(user1.getUsername())));
    }

    @Test
    @Order(2)
    void getUsers() throws Exception {
        // given - precondition or setup
        userList = userRepo.saveAll(userList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/user-management/v1/users")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[-1].username")
                        .value(userList.get(userList.size()-1).getUsername()));
    }

    @Test
    @Order(3)
    void saveUser() throws Exception {
        // given - precondition or setup

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/user-management/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1))
        );

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(user1.getUsername())));
    }

    @Test
    @Order(4)
    void getRoles() throws Exception {
        // given - precondition or setup
        roleList = roleRepo.saveAll(roleList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                get("/user-management/v1/roles")
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[-1].name")
                        .value(roleList.get(roleList.size()-1).getName()));
    }

    @Test
    @Order(5)
    void addRoleToUser() throws Exception {
        // given - precondition or setup
        user1 = userRepo.save(user1);
        role1 = roleRepo.save(role1);
        RoleToUserFormDto form = RoleToUserFormDto
                .builder()
                .roleName(role1.getName())
                .username(user1.getUsername())
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(
                post("/user-management/v1/role-to-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form))
        );

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(user1.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles.[0].name", Matchers.is(role1.getName())));
    }

    @Test
    @Order(6)
    void deleteUser() throws Exception {
        // given - precondition or setup
        user1 = userRepo.save(user1);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(
                delete("/user-management/v1/users/{id}",
                        user1.getId())
        );

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}