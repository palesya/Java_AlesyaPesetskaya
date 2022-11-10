package com.joint_walks.java_alesyapesetskaya.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidatorForEdit implements ConstraintValidator<ValidImageForEdit, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        boolean result = true;
        if (!multipartFile.isEmpty()) {
            String contentType = multipartFile.getContentType();
            if (!isSupportedContentType(contentType)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Only PNG or JPG images are allowed.")
                        .addConstraintViolation();
                result = false;
            }
        }
        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
