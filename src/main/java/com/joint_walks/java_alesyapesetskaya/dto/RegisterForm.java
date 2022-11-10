package com.joint_walks.java_alesyapesetskaya.dto;

import com.joint_walks.java_alesyapesetskaya.validator.ValidImage;
import com.joint_walks.java_alesyapesetskaya.validator.ValidLoginForRegister;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterForm {

    @ValidLoginForRegister
    private String login;
    @NotNull(message = "Age can't be empty.")
    @Min(value = 18, message = "Age can't be less than 18 years.")
    private Integer userAge;
    @NotBlank(message = "Password can't be empty or contain only spaces.")
    @Size(min = 4, max = 20, message = "Password should be in between 4 to 20 characters.")
    private String password;
    @NotBlank(message = "Repeat password can't be empty or contain only spaces.")
    @Size(min = 4, max = 20, message = "Repeat password should be in between 4 to 20 characters.")
    private String repeatPassword;
    @ValidImage
    private MultipartFile userImage;
    @NotBlank(message = "Name can't be empty or contain only spaces.")
    @Size(min = 2, max = 20, message = "Name should contain from 2 to 20 symbols.")
    private String dogName;
    @NotBlank(message = "Type can't be empty or contain only spaces.")
    @Size(min = 2, max = 40, message = "Type should contain from 2 to 40 symbols.")
    private String dogType;
    @NotNull(message = "Sex should be selected.")
    private String dogSexString;
    @NotNull(message = "Age can't be empty.")
    @Max(value = 30, message = "Age can't be greater than 30 years.")
    @Min(value = 0, message = "Age can't be less than 0 years.")
    private Integer dogAge;
    @ValidImage
    private MultipartFile dogImage;
    @AssertTrue(message = "'Password' and 'Repeat password' should be equal.")
    private boolean matchPass;

    @AssertTrue
    public boolean isConditionTrue() {
        this.matchPass = Objects.equals(this.password, this.repeatPassword);
        return matchPass;
    }
}
