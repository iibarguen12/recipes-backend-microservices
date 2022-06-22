package com.abn.authservice.service;

import com.abn.authservice.domain.Role;
import com.abn.authservice.domain.User;
import com.abn.authservice.dto.RoleToUserFormDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("${microservices.user.name}/user-management/v1")
public interface UserFeignService {
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id);

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers();

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user);

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles();

    @PostMapping("/role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFormDto form);
}
