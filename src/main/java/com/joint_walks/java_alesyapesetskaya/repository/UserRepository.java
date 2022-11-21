package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Query("select u from User u where u.login=:login and u.isDeleted=false")
    User getByLoginNotDeleted(String login);

    @Transactional
    @Query("select u from User u where u.login=:login")
    User getByLogin(String login);

    @Transactional
    @Query("select u from User u JOIN u.dog d " +
            "where u.isDeleted=false and (lower(u.login) LIKE lower(CONCAT('%',:text,'%')) OR LOWER(d.name) LIKE lower(CONCAT('%',:text,'%')) OR LOWER(d.type) LIKE lower(CONCAT('%',:text,'%')))")
    List<User> getUsersByPartialMatchNotDeleted(String text);

    @Transactional
    @Query("select u from User u where u.id=:id")
    User getById(Long id);

    @Transactional
    @Query("select u from User u join u.roles r where u.isDeleted=false and r.name='ROLE_USER'")
    List<User> findUsersByIsDeletedIsFalse();

    @Transactional
    @Query("select u from User u join u.roles r where u.isDeleted=true and r.name='ROLE_USER'")
    List<User> findUsersByIsDeletedIsTrue();

    @Transactional
    @Modifying
    @Query("update User u set u.isDeleted=true where u.id=:userId")
    void setIsDeletedToTrue(Long userId);

    @Transactional
    @Modifying
    @Query("update User u set u.isDeleted=false where u.id=:userId")
    void setIsDeletedToFalse(Long userId);

}
