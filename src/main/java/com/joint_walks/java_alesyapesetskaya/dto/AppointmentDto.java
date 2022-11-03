package com.joint_walks.java_alesyapesetskaya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {

    private Long id;
    private PlaceDto place;
    private Date date;
    private LocalTime time;
    private String description;
    private Integer numberOfPeople;

}
