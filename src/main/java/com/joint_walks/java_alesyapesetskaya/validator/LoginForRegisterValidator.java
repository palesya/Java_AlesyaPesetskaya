package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class LoginForRegisterValidator extends AbstractLoginValidator implements ConstraintValidator<ValidLoginForRegister, String> {

    public LoginForRegisterValidator(UserRepository repository) {
        super(repository);
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        boolean result = true;
        boolean lengthValid = isLengthValid(login, context);
        if (!lengthValid) {
            result = false;
        } else if (isLoginAlreadyExist(login, context)) {
            result = false;
        }
        return result;
    }
}
