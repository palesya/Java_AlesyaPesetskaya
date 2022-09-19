package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> getByLogin(String login) {
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
}
