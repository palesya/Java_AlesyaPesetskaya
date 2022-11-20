package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public abstract class AbstractLoginValidator {

    private final UserRepository repository;

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
        boolean isExist = false;
        for (String el : collect) {
            if (el.equalsIgnoreCase(login.trim())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Such login already exists.")
                        .addConstraintViolation();
                isExist = true;
                break;
            }
        }
        return isExist;
    }

}
