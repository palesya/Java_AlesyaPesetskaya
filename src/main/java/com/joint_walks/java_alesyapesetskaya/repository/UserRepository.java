package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    User getByLogin(String login);

    @Transactional
    @Query("select u from User u JOIN u.dog d " +
            "where lower(u.login) LIKE lower(CONCAT('%',:text,'%')) " +
            "OR LOWER(d.name) LIKE lower(CONCAT('%',:text,'%')) " +
            "OR LOWER(d.type) LIKE lower(CONCAT('%',:text,'%'))")
    List<User> getUsersByPartialMatch(String text);



}
