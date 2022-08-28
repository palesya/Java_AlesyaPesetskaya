package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getByLogin(String login);
    List<User> findByIsDeletedIsFalse();

}
