package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Mockito.when(appointmentService.getAddedUserIdsFromAppointment(1L)).thenReturn(List.of(1L,2L));
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
}