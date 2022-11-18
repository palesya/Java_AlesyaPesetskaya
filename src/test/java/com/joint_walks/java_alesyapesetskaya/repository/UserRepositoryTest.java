package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Suite.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    List<User> users = new ArrayList<>();

    @BeforeEach
    public void before() {
        repository.deleteAll();
        User user1 = User.builder()
                .login("Alesya")
                .password("123")
                .isDeleted(false)
                .build();

        User user2 = User.builder()
                .login("Pavel")
                .password("456")
                .isDeleted(false)
                .build();

        User user3 = User.builder()
                .login("Deleted")
                .password("789")
                .isDeleted(true)
                .build();

        users.add(user1);
        users.add(user2);
        users.add(user3);

        repository.saveAll(users);
    }

    @Test
    void getByExistingLoginNotDeleted() {
        User userFromDB = repository.getByLoginNotDeleted("Alesya");
        Assertions.assertNotNull(userFromDB);
    }

    @Test
    void getByNotExistingLoginNotDeleted() {
        User userFromDB = repository.getByLoginNotDeleted("test");
        Assertions.assertNull(userFromDB);
    }

    @Test
    void getByExistingLoginButDeleted() {
        User userFromDB = repository.getByLoginNotDeleted("Deleted");
        Assertions.assertNull(userFromDB);
    }

}