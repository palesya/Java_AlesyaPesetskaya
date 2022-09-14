package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public List<User> getByLogin(String login) {
        return repository.getByLogin(login);
    }

    @Override
    public List<User> getAllNotDeleted() {
        return repository.findAll().stream().filter(user -> !user.isDeleted()).collect(Collectors.toList());
    }
}
