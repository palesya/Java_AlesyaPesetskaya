package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
