package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapperUtils converter;

    @Test
    void getUserDtoByLogin() {
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        User userFromDB = User.builder()
                .login("test")
                .password("password")
                .id(20L)
                .build();
        UserDto userDto = UserDto.builder()
                .login("test")
                .id(20L)
                .build();
        Mockito.when(repository.getByLoginNotDeleted("test")).thenReturn(userFromDB);
        Mockito.when(converter.mapToUserDTO(userFromDB)).thenReturn(userDto);
        Long idUserDTO = service.getUserDtoByLogin("test").getId();
        Assertions.assertEquals(20L, idUserDTO);
    }

    @Test
    void getAllNotDeleted() {
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        User userFromDB1 = User.builder()
                .login("user1")
                .password("password")
                .isDeleted(false)
                .id(20L)
                .build();
        User userFromDB2 = User.builder()
                .login("user2")
                .password("password")
                .isDeleted(false)
                .id(21L)
                .build();
        List<User> usersFromDB = new ArrayList<>();
        usersFromDB.add(userFromDB1);
        usersFromDB.add(userFromDB2);

        UserDto userDto1 = UserDto.builder()
                .login("user1")
                .isDeleted(false)
                .id(20L)
                .build();
        UserDto userDto2 = UserDto.builder()
                .login("user2")
                .isDeleted(false)
                .id(21L)
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(userDto1);
        usersDto.add(userDto2);
        Mockito.when(repository.findUsersByIsDeletedIsFalse()).thenReturn(usersFromDB);
        Mockito.when(converter.mapToListUserDTO(usersFromDB)).thenReturn(usersDto);
        List<UserDto> allNotDeleted = service.getAllNotDeleted();
        Assertions.assertEquals(2, allNotDeleted.size());
        for (UserDto user : allNotDeleted) {
            Assertions.assertFalse(user.isDeleted());
        }
    }

    @Test
    void getUsersByPartialMatch() {
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        User userFromDB1 = User.builder()
                .login("user1")
                .password("password")
                .isDeleted(false)
                .id(20L)
                .build();
        User userFromDB2 = User.builder()
                .login("user2")
                .password("password")
                .isDeleted(false)
                .id(21L)
                .build();
        List<User> usersFromDB = new ArrayList<>();
        usersFromDB.add(userFromDB1);
        usersFromDB.add(userFromDB2);

        UserDto userDto1 = UserDto.builder()
                .login("user1")
                .isDeleted(false)
                .id(20L)
                .build();
        UserDto userDto2 = UserDto.builder()
                .login("user2")
                .isDeleted(false)
                .id(21L)
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(userDto1);
        usersDto.add(userDto2);

        Mockito.when(repository.getUsersByPartialMatchNotDeleted("us")).thenReturn(usersFromDB);
        Mockito.when(converter.mapToListUserDTO(usersFromDB)).thenReturn(usersDto);
        List<UserDto> foundUsers = service.getUsersByPartialMatch("us");
        Assertions.assertEquals(2, foundUsers.size());
        for (UserDto user : foundUsers) {
            Assertions.assertTrue(user.getLogin().contains("us"));
        }
    }

    @Test
    void getUserById() {
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        User userFromDB = User.builder()
                .login("user1")
                .password("password")
                .isDeleted(false)
                .id(20L)
                .build();
        Mockito.when(repository.getById(20L)).thenReturn(userFromDB);
        User userById = service.getUserById(20L);
        Assertions.assertEquals("user1", userById.getLogin());
    }

    @Test
    void getUserDtoById() {
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        User userFromDB = User.builder()
                .login("user1")
                .password("password")
                .isDeleted(false)
                .id(20L)
                .build();

        UserDto userDto = UserDto.builder()
                .login("user1")
                .isDeleted(false)
                .id(20L)
                .build();

        Mockito.when(repository.getById(20L)).thenReturn(userFromDB);
        Mockito.when(converter.mapToUserDTO(userFromDB)).thenReturn(userDto);

        UserDto userDtoById = service.getUserDtoById(20L);
        Assertions.assertNotNull(userDtoById);
        Assertions.assertEquals("user1", userDtoById.getLogin());
    }

    @Test
    void saveUser() {
        User user = User.builder()
                .login("test")
                .password("test")
                .build();
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        Mockito.when(repository.saveAndFlush(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        service.saveUser(user);
        User userFromDB = User.builder()
                .login("test")
                .password("password")
                .id(20L)
                .build();
        Mockito.when(repository.getByLoginNotDeleted("test")).thenReturn(userFromDB);
        Long idUserFromDB = service.getUserByLogin("test").getId();
        Assertions.assertEquals(20L, idUserFromDB);
    }

    @Test
    void setIsDeletedToTrue() {
        UserServiceImpl service = new UserServiceImpl(repository, converter);
        User userFromDB = User.builder()
                .login("user1")
                .password("password")
                .isDeleted(false)
                .id(20L)
                .build();
        Mockito.doAnswer(invocation -> {
            userFromDB.setDeleted(true);
            return null;
        }).when(repository).setIsDeletedToTrue(20L);
        service.setIsDeletedToTrue(20L);
        boolean deleted = userFromDB.isDeleted();
        Assertions.assertTrue(deleted);
    }
}