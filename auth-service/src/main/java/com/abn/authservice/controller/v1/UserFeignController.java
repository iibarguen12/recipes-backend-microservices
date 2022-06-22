package com.abn.authservice.controller.v1;

import com.abn.authservice.domain.Role;
import com.abn.authservice.domain.User;
import com.abn.authservice.dto.RoleToUserFormDto;
import com.abn.authservice.exception.ResourceNotFoundException;
import com.abn.authservice.service.UserFeignService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserFeignController {
    private final UserFeignService userFeignService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        try{
            return userFeignService.getUser(id);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers(){
        return userFeignService.getUsers();
    }

    @PostMapping("")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return userFeignService.saveUser(user);
    }
}

