package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapperUtils converter;

    @Override
    public UserDto getUserDtoByLogin(String login) {
        User userFromDB = repository.getByLoginNotDeleted(login);
        return converter.mapToUserDTO(userFromDB);
    }

    @Override
    public User getUserByLogin(String login) {
        return repository.getByLoginNotDeleted(login);
    }

    @Override
    public List<UserDto> getAllNotDeleted() {
        List<User> byIsDeletedIsFalse = repository.findUsersByIsDeletedIsFalse();
        return converter.mapToListUserDTO(byIsDeletedIsFalse);
    }

    @Override
    public List<UserDto> getAllDeleted() {
        List<User> usersByIsDeletedIsTrue = repository.findUsersByIsDeletedIsTrue();
        return converter.mapToListUserDTO(usersByIsDeletedIsTrue);
    }

    @Override
    public List<UserDto> getUsersByPartialMatch(String text) {
        List<User> usersFromDb = repository.getUsersByPartialMatchNotDeleted(text);
        return converter.mapToListUserDTO(usersFromDb);
    }

    @Override
    public User getUserById(Long id) {
        return repository.getById(id);
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        User userFromDB = repository.getById(id);
        return converter.mapToUserDTO(userFromDB);
    }

    @Override
    public void saveUser(User user) {
        repository.saveAndFlush(user);
    }

    @Override
    public void setIsDeletedToTrue(Long userId) {
        repository.setIsDeletedToTrue(userId);
    }

    @Override
    public void setIsDeletedToFalse(Long userId) {
        repository.setIsDeletedToFalse(userId);
    }

}
