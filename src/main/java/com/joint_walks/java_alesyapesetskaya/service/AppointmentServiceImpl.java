package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public List<Appointment> getAll(){
        return repository.findAll();
    }

    @Override
    public Appointment getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        repository.saveAndFlush(appointment);
    }

    @Override
    public List<Appointment> getAppointmentByCity(String city) {
        return repository.getAppointmentByCity(city);
    }

    @Override
    public List<Appointment> getAppointmentsByPartialMatch(String text) {
        return repository.getAppointmentsByPartialMatch(text);
    }

}
