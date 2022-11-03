package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.AppointmentMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.exception.UserIsAlreadyAddedException;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapperUtils converter;

    @Override
    public List<AppointmentDto> getAll() {
        List<Appointment> allFromDb = repository.findAll();
        return converter.mapToListAppointmentDTO(allFromDb);
    }

    @Override
    public Appointment getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void createAppointment(Appointment appointment, User user) {
        appointment.setNumberOfPeople(1);
        List<User> users = new ArrayList<>();
        users.add(user);
        appointment.setUsers(users);
        repository.saveAndFlush(appointment);
    }

    @Override
    public void joinAppointment(Appointment appointment, User user) {
        List<Long> addedUserIdsFromAppointment = getAddedUserIdsFromAppointment(appointment.getId());
        Long userId = user.getId();
        if (!addedUserIdsFromAppointment.contains(userId)) {
            List<User> users = appointment.getUsers();
            users.add(user);
            appointment.setUsers(users);
            Integer numberOfPeople = getNumberOfAddedUsers(appointment.getId());
            appointment.setNumberOfPeople(numberOfPeople);
            repository.saveAndFlush(appointment);
        } else {
            throw new UserIsAlreadyAddedException("You are already added to the selected appointment.");
        }
    }

    @Override
    public List<AppointmentDto> getAppointmentByCity(String city) {
        List<Appointment> appointmentByCity = repository.getAppointmentByCity(city);
        return converter.mapToListAppointmentDTO(appointmentByCity);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPartialMatch(String text) {
        List<Appointment> appointmentsByPartialMatch = repository.getAppointmentsByPartialMatch(text);
        return converter.mapToListAppointmentDTO(appointmentsByPartialMatch);
    }

    @Override
    public List<Long> getAddedUserIdsFromAppointment(Long appointmentId) {
        return repository.getAddedUserIdsFromAppointment(appointmentId);
    }

    @Override
    public Integer getNumberOfAddedUsers(Long appointmentId) {
        return repository.getNumberOfAddedUsers(appointmentId);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        repository.deleteById(appointmentId);
    }

    @Override
    public void deleteUserFromAppointments(Long userId) {
        List<Appointment> appointmentsByUserId = getAppointmentsByUserId(userId);
        for (Appointment appointment : appointmentsByUserId) {
            List<User> usersFromAppointment = appointment.getUsers();
            usersFromAppointment.removeIf(user -> Objects.equals(userId, user.getId()));
            Integer numberOfPeople = getNumberOfAddedUsers(appointment.getId());
            if (numberOfPeople == 0) {
                repository.delete(appointment);
            } else {
                appointment.setUsers(usersFromAppointment);
                appointment.setNumberOfPeople(numberOfPeople);
                repository.saveAndFlush(appointment);
            }
        }
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return repository.getAppointmentsByUserId(userId);
    }

}
