package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LoginForRegisterValidatorTest {

    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private UserRepository repository;
    
    @Test
    public void testValidLoginLength() {
        LoginForRegisterValidator validator = new LoginForRegisterValidator(repository);
        User user = User.builder()
                .login("Maria")
                .build();
        boolean valid = validator.isLengthValid(user.getLogin(), context);
        Assertions.assertTrue(valid);
    }

    @Test
    public void testInvalidLoginLength() {
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(builder);
        LoginForRegisterValidator validator = new LoginForRegisterValidator(repository);
        User userWithEmptyLogin = User.builder()
                .login("")
                .build();
        User userWithShortLogin = User.builder()
                .login("Max")
                .build();
        User userWithLongLogin = User.builder()
                .login("Maaaaaaaaaaaaaaaaaaaaaaaaaaaaax")
                .build();
        boolean isValidEmpty = validator.isValid(userWithEmptyLogin.getLogin(), context);
        Assertions.assertFalse(isValidEmpty);
        boolean isValidShort = validator.isValid(userWithShortLogin.getLogin(), context);
        Assertions.assertFalse(isValidShort);
        boolean isValidLong = validator.isValid(userWithLongLogin.getLogin(), context);
        Assertions.assertFalse(isValidLong);
    }

    @Test
    public void testUniqueLogin() {
        LoginForRegisterValidator validator = new LoginForRegisterValidator(repository);
        User user1 = User.builder()
                .login("Ivan")
                .build();
        User user2 = User.builder()
                .login("Alesya")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        Mockito.when(repository.findAll()).thenReturn(users);

        User user = User.builder()
                .login("Maria")
                .build();

        boolean isExist = validator.isLoginAlreadyExist(user.getLogin(), context);
        Assertions.assertFalse(isExist);
    }

    @Test
    public void testLoginAlreadyExists() {
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(builder);
        LoginForRegisterValidator validator = new LoginForRegisterValidator(repository);
        User user1 = User.builder()
                .login("Ivan")
                .build();
        User user2 = User.builder()
                .login("Alesya")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        Mockito.when(repository.findAll()).thenReturn(users);

        User user = User.builder()
                .login("Ivan")
                .build();

        boolean isExist = validator.isValid(user.getLogin(), context);
        Assertions.assertFalse(isExist);
    }

}