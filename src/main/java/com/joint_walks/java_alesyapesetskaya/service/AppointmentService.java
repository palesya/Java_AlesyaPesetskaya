package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.User;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAll();
    Appointment getById(Long id);
    void createAppointment(Appointment appointment, User user);
    void joinAppointment(Appointment appointment, User user);
    List<AppointmentDto> getAppointmentsByCity(String city);
    List<AppointmentDto> getAppointmentsByPartialMatch(String text) ;
    List<Long> getAddedUserIdsFromAppointment(Long appointmentId) ;
    Integer getNumberOfAddedUsers(Long appointmentId);
    void deleteAppointment(Long appointmentId) ;
    void deleteUserFromAppointments(Long userId) ;
    void deleteUserFromOneAppointment(Long userId, Long appointmentId);
    List<AppointmentDto> getAppointmentsWithoutUser(Long userId);
    List<AppointmentDto> excludeAppointmentsWithUser(Long userId, List<AppointmentDto> allAppointments);
    List<Appointment> getAppointmentsByUserId(Long userId);
}

