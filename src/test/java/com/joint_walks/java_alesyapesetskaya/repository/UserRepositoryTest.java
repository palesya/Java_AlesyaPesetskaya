package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    List<User> users = new ArrayList<>();

    @BeforeEach
    public void before() {
        repository.deleteAll();
        User user1 = User.builder()
                .login("Alesya")
                .password("123")
                .build();

        User user2 = User.builder()
                .login("Pavel")
                .password("456")
                .build();

        users.add(user1);
        users.add(user2);

        repository.saveAll(users);
    }

    @Test
    void getByLoginNotExist() {
        User testUser = repository.getByLoginNotDeleted("test");
        assertNull(testUser);
    }

}