package com.joint_walks.java_alesyapesetskaya.dto;

import com.joint_walks.java_alesyapesetskaya.validator.ValidImageForEdit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePersonalDataForm {

    @Min(value = 18, message = "Age can't be less than 18 years.")
    private Integer ownerAge;
    @Pattern(regexp = "|.{2,20}", message="Name should contain from 2 to 20 symbols.")
    private String dogName;
    @Pattern(regexp = "|.{2,40}", message="Type should contain from 2 to 40 symbols.")
    private String dogType;
    @Max(value = 30, message = "Age can't be greater than 30 years.")
    @Min(value = 0, message = "Age can't be less than 0 years.")
    private Integer dogAge;
    @ValidImageForEdit
    private MultipartFile ownerImage;
    @ValidImageForEdit
    private MultipartFile dogImage;
    private String dogSexString;

}
