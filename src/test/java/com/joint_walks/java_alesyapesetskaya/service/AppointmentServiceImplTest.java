package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.AppointmentMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository repository;
    @Mock
    private AppointmentMapperUtils converter;


    @Test
    void getAll() {

    }

    @Test
    void getById() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        Appointment appointment = Appointment.builder()
                .time(LocalTime.of(20, 30))
                .numberOfPeople(2)
                .build();
        Mockito.when(repository.getById(20L)).thenReturn(appointment);
        Appointment appointmentById = service.getById(20L);
        Assertions.assertEquals(LocalTime.of(20, 30), appointmentById.getTime());
    }

    @Test
    void createAppointment() {

    }

    @Test
    void joinAppointment() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("user2")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user2);
        users.add(user1);
        User user3 = User.builder()
                .id(3L)
                .login("user3")
                .build();
        Appointment appointment = Appointment.builder()
                .time(LocalTime.of(20, 30))
                .numberOfPeople(2)
                .users(users)
                .build();
        Mockito.doAnswer(invocation -> {
            appointment.setNumberOfPeople(3);
            users.add(user3);
            appointment.setUsers(users);
            return null;
        }).when(repository).saveAndFlush(appointment);
        service.joinAppointment(appointment, user3);
        Assertions.assertEquals(3, appointment.getNumberOfPeople());
        Assertions.assertTrue(appointment.getUsers().contains(user3));
    }

    @Test
    void getAppointmentByCity() {
    }

    @Test
    void getAppointmentsByPartialMatch() {
    }

    @Test
    void getAddedUserIdsFromAppointment() {
    }

    @Test
    void getNumberOfAddedUsers() {
    }

    @Test
    void deleteAppointment() {
    }

    @Test
    void deleteUserFromAppointments() {
    }

    @Test
    void deleteUserFromOneAppointmentWhenNotAloneInAppointment() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("user2")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user2);
        users.add(user1);
        Appointment appointment = Appointment.builder()
                .users(users)
                .id(1L)
                .numberOfPeople(2)
                .build();
        Mockito.when(repository.getById(1L)).thenReturn(appointment);
        Mockito.when(repository.getNumberOfAddedUsers(1L)).thenReturn(2);
        Mockito.doAnswer(invocation -> {
            users.remove(user1);
            appointment.setNumberOfPeople(1);
            appointment.setUsers(users);
            return null;
        }).when(repository).saveAndFlush(appointment);
        service.deleteUserFromOneAppointment(1L, 1L);
        Assertions.assertEquals(1, appointment.getNumberOfPeople());
        Assertions.assertTrue(appointment.getUsers().contains(user2));
    }

    @Test
    void deleteUserFromOneAppointmentWhenAloneInAppointment() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        Appointment appointment = Appointment.builder()
                .users(users)
                .id(1L)
                .numberOfPeople(1)
                .build();
        Mockito.when(repository.getById(1L)).thenReturn(appointment);
        Mockito.when(repository.getNumberOfAddedUsers(1L)).thenReturn(2);
        Mockito.doAnswer(invocation -> {
            Mockito.when(repository.getById(1L)).thenReturn(null);
            return null;
        }).when(repository).delete(appointment);
        service.deleteUserFromOneAppointment(1L, 1L);
        Assertions.assertNull(service.getById(1L));

    }

    @Test
    void getAppointmentsWithoutUser() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        List<Long> appointmentsIds = new ArrayList<>();
        appointmentsIds.add(1L);
        appointmentsIds.add(2L);
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("user2")
                .build();
        User user3 = User.builder()
                .id(3L)
                .login("user3")
                .build();
        List<User> usersWithoutUser3 = new ArrayList<>();
        usersWithoutUser3.add(user2);
        usersWithoutUser3.add(user1);
        Appointment appointment1 = Appointment.builder()
                .users(usersWithoutUser3)
                .id(1L)
                .build();
        Appointment appointment2 = Appointment.builder()
                .users(usersWithoutUser3)
                .id(2L)
                .build();
        List<Appointment> appointmentsWithoutUser3 = new ArrayList<>();
        appointmentsWithoutUser3.add(appointment1);
        appointmentsWithoutUser3.add(appointment2);

        AppointmentDto appointmentDto1 = AppointmentDto.builder()
                .users(usersWithoutUser3)
                .id(1L)
                .build();
        AppointmentDto appointmentDto2 = AppointmentDto.builder()
                .users(usersWithoutUser3)
                .id(2L)
                .build();
        List<AppointmentDto> appointmentsDto = new ArrayList<>();
        appointmentsDto.add(appointmentDto1);
        appointmentsDto.add(appointmentDto2);

        Mockito.when(repository.getAppointmentsIdsWithoutUserId(3L)).thenReturn(appointmentsIds);
        Mockito.when(repository.getById(1L)).thenReturn(appointment1);
        Mockito.when(repository.getById(2L)).thenReturn(appointment2);
        Mockito.when(converter.mapToListAppointmentDTO(appointmentsWithoutUser3)).thenReturn(appointmentsDto);
        List<AppointmentDto> appointmentsWithoutUser = service.getAppointmentsWithoutUser(3L);
        for (AppointmentDto appointment : appointmentsWithoutUser) {
            boolean containsId3 = appointment.getUsers().stream().map(User::getId).collect(Collectors.toList()).contains(user3.getId());
            Assertions.assertFalse(containsId3);
        }
        Assertions.assertEquals(2, appointmentsWithoutUser.size());
    }

    @Test
    void excludeAppointmentsWithUser() {
    }

    @Test
    void getAppointmentsByUserId() {
    }
}