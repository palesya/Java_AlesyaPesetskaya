package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractLoginValidator {

    @Autowired
    UserRepository repository;

    public boolean isLengthValid(String login, ConstraintValidatorContext context) {
        if (login.isEmpty() || login.trim().length() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Login can't be empty or contain only spaces.")
                    .addConstraintViolation();
            return false;
        } else if (login.trim().getBytes().length < 4 || login.trim().getBytes().length > 30) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Login should contain from 4 to 30 symbols.")
                    .addConstraintViolation();
            return false;
        } else return true;
    }

    public boolean isLoginAlreadyExist(String login, ConstraintValidatorContext context) {
        List<String> collect = repository.findAll().stream().map(User::getLogin).collect(Collectors.toList());
        if (collect.contains(login.trim())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Such login already exists.")
                    .addConstraintViolation();
            return true;
        } else return false;
    }

}
