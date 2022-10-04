package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User getByLogin(String login) {
        return repository.getByLogin(login);
    }

    @Override
    public List<User> getAllNotDeleted() {
        return repository.findAll().stream().filter(user -> !user.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersByPartialMatch(String text) {
        return repository.getUsersByPartialMatch(text);
    }

    @Override
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void saveUser(User user) {
        repository.saveAndFlush(user);
    }

}
