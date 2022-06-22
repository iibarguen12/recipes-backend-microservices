package com.abn.userservice.service;

import com.abn.userservice.domain.Role;
import com.abn.userservice.domain.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    List<User> getUsers();
    User saveUser(User user);
    List<Role> getRoles();
    Role saveRole(Role role);
    User addRoleToUser(String username, String roleName);

    void deleteUserById(Long id);

}
