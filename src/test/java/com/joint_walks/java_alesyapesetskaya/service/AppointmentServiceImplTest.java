package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.AppointmentMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RunWith(Suite.class)
@ExtendWith({MockitoExtension.class})
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository repository;
    @Mock
    private AppointmentMapperUtils converter;


    @Test
    void getAll() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        Appointment appointment1 = Appointment.builder()
                .id(1L)
                .build();
        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .build();
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(appointment1);
        allAppointments.add(appointment2);
        AppointmentDto appointmentDto1 = AppointmentDto.builder()
                .id(1L)
                .build();
        AppointmentDto appointmentDto2 = AppointmentDto.builder()
                .id(2L)
                .build();
        List<AppointmentDto> allAppointmentsDto = new ArrayList<>();
        allAppointmentsDto.add(appointmentDto1);
        allAppointmentsDto.add(appointmentDto2);
        Mockito.when(repository.findAll()).thenReturn(allAppointments);
        Mockito.when(converter.mapToListAppointmentDTO(allAppointments)).thenReturn(allAppointmentsDto);
        List<AppointmentDto> all = service.getAll();
        Assertions.assertEquals(2, all.size());
        List<Long> allIds = all.stream().map(AppointmentDto::getId).collect(Collectors.toList());
        Assertions.assertTrue(allIds.contains(1L));
        Assertions.assertTrue(allIds.contains(2L));
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
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        Appointment appointment = Appointment.builder()
                .time(LocalTime.of(20, 30))
                .build();
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        List<User> users = new ArrayList<>();
        Mockito.doAnswer(invocation -> {
            appointment.setNumberOfPeople(1);
            users.add(user1);
            appointment.setUsers(users);
            appointment.setId(1L);
            return null;
        }).when(repository).saveAndFlush(appointment);
        service.createAppointment(appointment, user1);
        Assertions.assertEquals(1, appointment.getNumberOfPeople());
        Assertions.assertEquals(1L, appointment.getId());
        Assertions.assertTrue(appointment.getUsers().contains(user1));
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
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        String city = "Minsk";
        Address address1 = Address.builder()
                .city(city)
                .id(1L)
                .build();
        Place place1 = Place.builder()
                .address(address1)
                .id(1L)
                .build();
        Place place2 = Place.builder()
                .address(address1)
                .id(2L)
                .build();
        Appointment appointment1 = Appointment.builder()
                .id(1L)
                .place(place1)
                .build();
        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .place(place2)
                .build();
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(appointment1);
        allAppointments.add(appointment2);

        PlaceDto placeDto1 = PlaceDto.builder()
                .address(address1)
                .id(1L)
                .build();
        PlaceDto placeDto2 = PlaceDto.builder()
                .address(address1)
                .id(2L)
                .build();
        AppointmentDto appointmentDto1 = AppointmentDto.builder()
                .id(1L)
                .place(placeDto1)
                .build();
        AppointmentDto appointmentDto2 = AppointmentDto.builder()
                .id(2L)
                .place(placeDto2)
                .build();
        List<AppointmentDto> allAppointmentsDto = new ArrayList<>();
        allAppointmentsDto.add(appointmentDto1);
        allAppointmentsDto.add(appointmentDto2);

        Mockito.when(repository.getAppointmentsByCity(city)).thenReturn(allAppointments);
        Mockito.when(converter.mapToListAppointmentDTO(allAppointments)).thenReturn(allAppointmentsDto);
        List<AppointmentDto> appointmentsByCity = service.getAppointmentsByCity(city);
        List<String> collectCities = appointmentsByCity.stream().map(AppointmentDto::getPlace)
                .map(PlaceDto::getAddress)
                .map(Address::getCity)
                .collect(Collectors.toList());
        for (String el : collectCities) {
            Assertions.assertEquals(city, el);
        }
        Assertions.assertEquals(2, appointmentsByCity.size());

    }


    @Test
    void getAppointmentsByPartialMatch() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        Address address1 = Address.builder()
                .street("Svetlaya")
                .id(1L)
                .build();
        Address address2 = Address.builder()
                .street("Nice")
                .id(1L)
                .build();
        Place place1 = Place.builder()
                .address(address1)
                .id(1L)
                .build();
        Place place2 = Place.builder()
                .address(address2)
                .transportStop("Novaya Borovaya")
                .id(2L)
                .build();
        Appointment appointment1 = Appointment.builder()
                .id(1L)
                .place(place1)
                .build();
        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .place(place2)
                .build();
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(appointment1);
        allAppointments.add(appointment2);

        PlaceDto placeDto1 = PlaceDto.builder()
                .address(address1)
                .id(1L)
                .build();
        PlaceDto placeDto2 = PlaceDto.builder()
                .address(address2)
                .transportStop("Novaya Borovaya")
                .id(2L)
                .build();
        AppointmentDto appointmentDto1 = AppointmentDto.builder()
                .id(1L)
                .place(placeDto1)
                .build();
        AppointmentDto appointmentDto2 = AppointmentDto.builder()
                .id(2L)
                .place(placeDto2)
                .build();
        List<AppointmentDto> allAppointmentsDto = new ArrayList<>();
        allAppointmentsDto.add(appointmentDto1);
        allAppointmentsDto.add(appointmentDto2);

        Mockito.when(repository.getAppointmentsByPartialMatch("aya")).thenReturn(allAppointments);
        Mockito.when(converter.mapToListAppointmentDTO(allAppointments)).thenReturn(allAppointmentsDto);
        List<AppointmentDto> foundAppointments = service.getAppointmentsByPartialMatch("aya");
        Assertions.assertEquals(2, foundAppointments.size());

    }

    @Test
    void getAddedUserIdsFromAppointment() {
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
        Mockito.when(repository.getAddedUserIdsFromAppointment(1L)).thenReturn(List.of(1L, 2L));
        List<Long> addedUserIdsFromAppointment = service.getAddedUserIdsFromAppointment(1L);
        Assertions.assertEquals(2, addedUserIdsFromAppointment.size());
        Assertions.assertTrue(addedUserIdsFromAppointment.contains(1L));
        Assertions.assertTrue(addedUserIdsFromAppointment.contains(2L));
    }

    @Test
    void deleteAppointment() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        Mockito.doAnswer(invocation -> {
            Mockito.when(repository.getById(1L)).thenReturn(null);
            return null;
        }).when(repository).deleteById(1L);
        service.deleteAppointment(1L);
        Appointment byId = service.getById(1L);
        Assertions.assertNull(byId);
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
        Mockito.when(repository.getNumberOfAddedUsers(1L)).thenReturn(1);
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
        Mockito.when(repository.getNumberOfAddedUsers(1L)).thenReturn(0);
        Mockito.doAnswer(invocation -> {
            Mockito.when(repository.getById(1L)).thenReturn(null);
            return null;
        }).when(repository).delete(appointment);
        service.deleteUserFromOneAppointment(1L, 1L);
        Assertions.assertNull(service.getById(1L));

    }

    @Test
    void deleteUserFromAppointmentsAloneInAppointment() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("user2")
                .build();
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user2);
        allUsers.add(user1);
        List<User> oneUser = new ArrayList<>();
        oneUser.add(user1);
        Appointment appointment1 = Appointment.builder()
                .users(allUsers)
                .id(1L)
                .numberOfPeople(2)
                .build();
        Appointment appointment2 = Appointment.builder()
                .users(oneUser)
                .id(2L)
                .numberOfPeople(1)
                .build();
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment1);
        appointments.add(appointment2);
        Mockito.when(repository.getAppointmentsByUserId(1L)).thenReturn(appointments);

        Mockito.when(repository.getNumberOfAddedUsers(1L)).thenReturn(1);
        Mockito.when(repository.getNumberOfAddedUsers(2L)).thenReturn(0);
        Mockito.doAnswer(invocation -> {
            allUsers.remove(user1);
            appointment1.setNumberOfPeople(1);
            appointment1.setUsers(allUsers);
            return null;
        }).when(repository).saveAndFlush(appointment1);
        Mockito.doAnswer(invocation -> {
            Mockito.when(repository.getById(2L)).thenReturn(null);
            return null;
        }).when(repository).delete(appointment2);
        service.deleteUserFromAppointments(1L);
        Assertions.assertEquals(1, appointment1.getNumberOfPeople());
        Assertions.assertTrue(appointment1.getUsers().contains(user2));
        Assertions.assertNull(service.getById(2L));
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
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
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

        List<User> usersWithUser3 = new ArrayList<>();
        usersWithUser3.add(user1);
        usersWithUser3.add(user2);
        usersWithUser3.add(user3);

        AppointmentDto appointment1 = AppointmentDto.builder()
                .users(usersWithoutUser3)
                .id(1L)
                .build();
        AppointmentDto appointment2 = AppointmentDto.builder()
                .users(usersWithUser3)
                .id(2L)
                .build();

        List<AppointmentDto> appointmentsWithUser3 = new ArrayList<>();
        appointmentsWithUser3.add(appointment1);
        appointmentsWithUser3.add(appointment2);

        Mockito.when(repository.getAppointmentsIdsWithoutUserId(3L)).thenReturn(List.of(1L));
        List<AppointmentDto> resultedAppointments = service.excludeAppointmentsWithUser(3L, appointmentsWithUser3);
        Assertions.assertEquals(1, resultedAppointments.size());

    }

    @Test
    void getAppointmentsByUserId() {
        AppointmentServiceImpl service = new AppointmentServiceImpl(repository, converter);
        User user1 = User.builder()
                .id(1L)
                .login("user1")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        Appointment appointment1=Appointment.builder()
                .id(1L)
                .users(users)
                .build();
        Appointment appointment2=Appointment.builder()
                .id(2L)
                .users(users)
                .build();
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(appointment1);
        allAppointments.add(appointment2);
        Mockito.when(repository.getAppointmentsByUserId(1L)).thenReturn(allAppointments);
        List<Appointment> appointmentsByUserId = service.getAppointmentsByUserId(1L);
        Assertions.assertEquals(2,appointmentsByUserId.size());
    }

}