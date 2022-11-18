package com.joint_walks.java_alesyapesetskaya.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;
@RunWith(Suite.class)
@ExtendWith(MockitoExtension.class)
public class ImageFileValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testValidFile() {
        String contentType = "image/png";
        byte[] content = "some content".getBytes(StandardCharsets.UTF_8);
        MultipartFile multipartFile = new MockMultipartFile("new", "new", contentType, content);
        ImageFileValidator validator = new ImageFileValidator();
        boolean isFileValid = validator.isValid(multipartFile,context);
        Assertions.assertTrue(isFileValid);
    }

    @Test
    public void testInvalidFile() {
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(builder);
        String contentType = "text/plain";
        byte[] content = "some content".getBytes(StandardCharsets.UTF_8);
        MultipartFile multipartFile = new MockMultipartFile("new", "new", contentType, content);
        ImageFileValidator validator = new ImageFileValidator();
        boolean isFileValid = validator.isValid(multipartFile,context);
        Assertions.assertFalse(isFileValid);
    }

    @Test
    public void testEmptyFile() {
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(builder);
        String contentType = "text/plain";
        byte[] content = null;
        MultipartFile multipartFile = new MockMultipartFile("new", "new", contentType, content);
        ImageFileValidator validator = new ImageFileValidator();
        boolean isFileValid = validator.isValid(multipartFile,context);
        Assertions.assertFalse(isFileValid);
    }

}