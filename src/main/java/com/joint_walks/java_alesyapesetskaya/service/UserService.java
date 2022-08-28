package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.User;

import java.util.List;

public interface UserService {

    List<User> getByLogin(String login);
    List<User> getAllNotDeleted();

}
