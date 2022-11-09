package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TimeValidator implements ConstraintValidator<ValidTime, AddAppointmentForm> {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public boolean isValid(AddAppointmentForm appointmentForm, ConstraintValidatorContext context) {
        boolean result = true;
        Date date = appointmentForm.getDate();
        LocalTime time = appointmentForm.getTime();
        Address address = appointmentForm.getAddress();
        List<LocalTime> timeOfAppointments = repository.findByDateAndAddress(date,address).stream().map(Appointment::getTime).collect(Collectors.toList());
        for (LocalTime timeFromDb : timeOfAppointments) {
            if (time.isAfter(timeFromDb.minusMinutes(59))&&time.isBefore(timeFromDb.plusMinutes(59))){
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
