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
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
public class RoleFeignController {
    private final UserFeignService userFeignService;

    @GetMapping("")
    public ResponseEntity<List<Role>> getRoles(){
        return userFeignService.getRoles();
    }

    @PostMapping("/user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFormDto form){
        try{
            return userFeignService.addRoleToUser(form);
        }catch (FeignException e){
            throw new ResourceNotFoundException();
        }
    }
}

