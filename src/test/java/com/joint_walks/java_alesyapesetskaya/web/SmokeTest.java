package com.joint_walks.java_alesyapesetskaya.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@RunWith(Suite.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    MainController controller;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(controller);
    }

}