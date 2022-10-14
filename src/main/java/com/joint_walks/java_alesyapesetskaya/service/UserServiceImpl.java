package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
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
    private final UserMapperUtils converter;

    @Override
    public UserDto getByLogin(String login) {
        User userFromDB = repository.getByLogin(login);
        return converter.mapToUserDTO(userFromDB);
    }

    @Override
    public List<UserDto> getAllNotDeleted() {
        return repository
                .findByIsDeletedIsFalse()
                .stream()
                .map(converter::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByPartialMatch(String text) {
        return repository
                .getUsersByPartialMatch(text)
                .stream()
                .map(converter::mapToUserDTO)
                .collect(Collectors.toList());
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

}
