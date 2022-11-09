package com.joint_walks.java_alesyapesetskaya.dto;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.validator.ValidTime;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidTime
public class AddAppointmentForm {

    @NotNull(message = "Address should be selected.")
    private Address address;
    @NotNull(message = "Date should be selected.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent(message = "Date should be no earlier than today.")
    private Date date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Time should be selected.")
    private LocalTime time;
    private String description;

}
