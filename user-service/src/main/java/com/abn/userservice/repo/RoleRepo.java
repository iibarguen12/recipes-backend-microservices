package com.abn.userservice.repo;

import com.abn.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo  extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
