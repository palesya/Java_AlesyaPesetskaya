package com.joint_walks.java_alesyapesetskaya.service;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.User;

import java.util.List;

public interface UserService {

    UserDto getUserDtoByLogin(String login);
    User getUserByLogin(String login);
    List<UserDto> getAllNotDeleted();
    List<UserDto> getAllDeleted();
    List<UserDto> getUsersByPartialMatch(String text);
    UserDto getUserDtoById(Long id);
    User getUserById(Long id);
    void saveUser(User user);
    void setIsDeletedToTrue(Long userId);
    void setIsDeletedToFalse(Long userId);
}
