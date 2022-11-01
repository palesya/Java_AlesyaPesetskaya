package com.joint_walks.java_alesyapesetskaya.dto;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDto {

    private Long id;
    private Address address;
    private String transportStop;
    private boolean trainingEqyipment;
    private boolean fence;
    private String base64Image;

}
