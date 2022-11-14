package com.joint_walks.java_alesyapesetskaya.validator;

import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TimeValidatorTest {

    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private AppointmentRepository repository;

    @Test
    public void testValidTimePlusHour() {
        Date date = new Date(2022,12,12);
        Address address = new Address("Minsk","Svetlaya",45);
        LocalTime time = LocalTime.of(20,30);
        LocalTime timePlusHour = time.plusHours(1);
        Place place = Place.builder()
                .address(address)
                .build();
        AddAppointmentForm appointmentForm = AddAppointmentForm.builder()
                .date(date)
                .time(timePlusHour)
                .address(address)
                .build();
        TimeValidator timeValidator = new TimeValidator(repository);

        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(new Appointment(place,date,time,"new description"));
        Mockito.when(repository.findByDateAndAddress(date,address)).thenReturn(allAppointments);
        boolean isValidTime = timeValidator.isValid(appointmentForm, context);
        Assertions.assertTrue(isValidTime);
    }

    @Test
    public void testInvalidTimePlusThirtyMinutes() {
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(builder);
        Date date = new Date(2022,12,12);
        Address address = new Address("Minsk","Svetlaya",45);
        LocalTime time = LocalTime.of(20,30);
        LocalTime timePlusHour = time.plusMinutes(30);
        Place place = Place.builder()
                .address(address)
                .build();
        AddAppointmentForm appointmentForm = AddAppointmentForm.builder()
                .date(date)
                .time(timePlusHour)
                .address(address)
                .build();
        TimeValidator timeValidator = new TimeValidator(repository);

        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(new Appointment(place,date,time,"new description"));
        Mockito.when(repository.findByDateAndAddress(date,address)).thenReturn(allAppointments);
        boolean isValidTime = timeValidator.isValid(appointmentForm, context);
        Assertions.assertFalse(isValidTime);
    }

}