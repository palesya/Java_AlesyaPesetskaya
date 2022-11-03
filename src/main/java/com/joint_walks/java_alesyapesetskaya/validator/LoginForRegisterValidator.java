package com.joint_walks.java_alesyapesetskaya.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginForRegisterValidator extends AbstractLoginValidator implements ConstraintValidator<ValidLoginForRegister, String> {

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
