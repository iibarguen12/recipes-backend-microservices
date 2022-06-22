package com.abn.userservice.service;


import com.abn.userservice.domain.User;
import com.abn.userservice.domain.Role;
import com.abn.userservice.exception.ResourceNotFoundException;
import com.abn.userservice.repo.RoleRepo;
import com.abn.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public User addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        if (user.getRoles() == null) {
            user.setRoles(Collections.singletonList(role));
        } else {
            user.getRoles().add(role);
        }
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

}
