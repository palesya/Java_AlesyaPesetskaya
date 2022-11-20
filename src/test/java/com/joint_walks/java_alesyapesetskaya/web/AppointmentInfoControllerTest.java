package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(Suite.class)
@SpringBootTest
@AutoConfigureMockMvc
@Execution(ExecutionMode.SAME_THREAD)
public class AppointmentInfoControllerTest {

    @MockBean
    UserServiceImpl userService;

    @MockBean
    AppointmentServiceImpl appointmentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void openWithAddedUser() throws Exception {
        UserDto loggedUser = UserDto.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();
        UserDto userDto2 = UserDto.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(loggedUser);
        usersDto.add(userDto2);

        User user1 = User.builder()
                .id(1L)
                .login("User")
                .password("User")
                .isDeleted(false)
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Appointment appointment = Appointment.builder()
                .id(1L)
                .users(users)
                .build();

        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
        Mockito.when(userService.getAllNotDeleted()).thenReturn(usersDto);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUser);
        Mockito.when(appointmentService.getById(1L)).thenReturn(appointment);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(user1);
        Mockito.when(appointmentService.getAddedUserIdsFromAppointment(1L)).thenReturn(List.of(1L, 2L));
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/appointment/1")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allUsers", users))
                .andExpect(model().attribute("appointment", appointment))
                .andExpect(model().attribute("loggedUser", loggedUser))
                .andExpect(model().attribute("isUserAdded", true));
    }

    @Test
    void openWithNotAddedUser() throws Exception {
        UserDto loggedUser = UserDto.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();
        UserDto userDto2 = UserDto.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(loggedUser);
        usersDto.add(userDto2);

        User user1 = User.builder()
                .id(1L)
                .login("User")
                .password("User")
                .isDeleted(false)
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user2);

        Appointment appointment = Appointment.builder()
                .id(1L)
                .users(users)
                .build();

        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
        Mockito.when(userService.getAllNotDeleted()).thenReturn(usersDto);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUser);
        Mockito.when(appointmentService.getById(1L)).thenReturn(appointment);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(user1);
        Mockito.when(appointmentService.getAddedUserIdsFromAppointment(1L)).thenReturn(List.of(2L));
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/appointment/1")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allUsers", users))
                .andExpect(model().attribute("appointment", appointment))
                .andExpect(model().attribute("loggedUser", loggedUser))
                .andExpect(model().attribute("isUserAdded", false));
    }

    @Test
    void leaveAppointmentWhenNotAloneUser() throws Exception {
        UserDto loggedUserDto = UserDto.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();

        User loggedUser = User.builder()
                .id(1L)
                .login("User")
                .password("User")
                .isDeleted(false)
                .build();
        User user2 = User.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user2);
        users.add(loggedUser);
        Address address = Address.builder()
                .city("Minsk")
                .street("Long")
                .houseNumberNearby(100)
                .build();
        Place place = Place.builder()
                .address(address)
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .place(place)
                .date(new Date(2020,10,10))
                .time(LocalTime.of(20,20))
                .users(users)
                .build();

        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUserDto);
        Mockito.when(appointmentService.getById(1L)).thenReturn(appointment);
        Mockito.doAnswer(invocation ->
        {
            users.remove(loggedUser);
            appointment.setUsers(users);
            return null;
        }).when(appointmentService).deleteUserFromOneAppointment(1L, 1L);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(loggedUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/appointment/1/leaveAppointment/1")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allUsers", users))
                .andExpect(model().attribute("appointment", appointment))
                .andExpect(model().attribute("loggedUser", loggedUserDto))
                .andExpect(model().attribute("success", "You've successfully left the appointment. Date: 10-11-3920. Time: 20:20. Address: Minsk, Long str. 100"))
                .andExpect(model().attribute("isUserAdded", false));
        Assertions.assertEquals(1,appointment.getUsers().size());
    }

    @Test
    void leaveAppointmentWhenAloneUser() throws Exception {
        UserDto loggedUserDto = UserDto.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();

        User loggedUser = User.builder()
                .id(1L)
                .login("User")
                .password("User")
                .isDeleted(false)
                .build();

        List<User> users = new ArrayList<>();
        users.add(loggedUser);
        Address address = Address.builder()
                .city("Minsk")
                .street("Long")
                .houseNumberNearby(100)
                .build();
        Place place = Place.builder()
                .address(address)
                .build();

        Appointment appointment = Appointment.builder()
                .id(1L)
                .place(place)
                .date(new Date(2020,10,10))
                .time(LocalTime.of(20,20))
                .users(users)
                .build();
        Appointment anotherAppointment = Appointment.builder()
                .id(2L)
                .place(place)
                .users(users)
                .build();
        List<Appointment> allUserAppointments = new ArrayList<>();
        allUserAppointments.add(anotherAppointment);
        allUserAppointments.add(appointment);
        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
        Mockito.when(userService.getUserDtoById(1L)).thenReturn(loggedUserDto);
        Mockito.when(appointmentService.getAppointmentsByUserId(1L)).thenReturn(allUserAppointments);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUserDto);
        Mockito.doAnswer(invocation ->
        {
            appointmentService.deleteAppointment(1L);
            allUserAppointments.remove(appointment);
            return null;
        }).when(appointmentService).deleteUserFromOneAppointment(1L, 1L);
        Mockito.when(appointmentService.getById(1L)).thenReturn(appointment).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/appointment/1/leaveAppointment/1")
                        .with(user(userDetails)))
                .andExpect(view().name("personalPage"))
                .andExpect(model().attribute("allUserAppointments", allUserAppointments))
                .andExpect(model().attribute("success", "You've successfully left the appointment. Date: 10-11-3920. Time: 20:20. Address: Minsk, Long str. 100"))
                .andExpect(model().attribute("loggedUser", loggedUserDto));
        Assertions.assertEquals(1,allUserAppointments.size());
    }
}