package com.joint_walks.java_alesyapesetskaya.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

    @Autowired
    MainController controller;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(controller);
    }

}