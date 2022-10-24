package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.AppointmentMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.exception.UserIsAlreadyAddedException;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<User> users = appointment.getUsers();
        if (!users.contains(user)) {
            Integer numberOfPeople = appointment.getNumberOfPeople();
            appointment.setNumberOfPeople(numberOfPeople + 1);
            users.add(user);
            appointment.setUsers(users);
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
    public List<Appointment> getAppointmentsByPartialMatch(String text) {
        return repository.getAppointmentsByPartialMatch(text);
    }

}
