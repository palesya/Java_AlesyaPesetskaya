package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TimeValidator implements ConstraintValidator<ValidTime, AddAppointmentForm> {

    private final AppointmentRepository repository;

    @Override
    public boolean isValid(AddAppointmentForm appointmentForm, ConstraintValidatorContext context) {
        boolean result = true;
        Date date = appointmentForm.getDate();
        LocalTime time = appointmentForm.getTime();
        Address address = appointmentForm.getAddress();
        List<LocalTime> timeOfAppointments = repository.findByDateAndAddress(date,address).stream().map(Appointment::getTime).collect(Collectors.toList());
        for (LocalTime timeFromDb : timeOfAppointments) {
            if (time.isAfter(timeFromDb.minusMinutes(60))&&time.isBefore(timeFromDb.plusMinutes(60))){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "This time is busy by another appointment.")
                        .addConstraintViolation();
                return false;
            }
        }
        return result;
    }


}
