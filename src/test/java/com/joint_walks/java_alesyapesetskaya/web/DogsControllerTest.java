package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
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
public class DogsControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/dogs")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void searchDogForAdmin() throws Exception {
        UserDto user1 = UserDto.builder()
                .id(1L)
                .login("Amops")
                .build();
        UserDto user2 = UserDto.builder()
                .id(1L)
                .login("Opmops")
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(user1);
        usersDto.add(user2);
        Mockito.when(userService.getUsersByPartialMatch("mops")).thenReturn(usersDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/admin/dogs")
                        .with(user("Admin").password("Admin").roles("ADMIN"))
                        .param("search_text", "mops"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUser() throws Exception {
        UserDto user1 = UserDto.builder()
                .id(1L)
                .login("Amops")
                .isDeleted(false)
                .build();
        UserDto user2 = UserDto.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        User userA = User.builder()
                .id(1L)
                .login("Amops")
                .isDeleted(false)
                .build();
        User userB = User.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(user1);
        usersDto.add(user2);
        List<User> users = new ArrayList<>();
        users.add(userA);
        users.add(userB);
        Appointment appointment = Appointment.builder()
                .users(users)
                .id(1L)
                .build();

        Mockito.doAnswer(i -> {
            user1.setDeleted(true);
            return null;
        }).when(userService).setIsDeletedToTrue(1L);
        Mockito.doAnswer(i -> {
            users.remove(userA);
            appointment.setUsers(users);
            return null;
        }).when(appointmentService).deleteUserFromAppointments(1L);
        Mockito.when(userService.getAllNotDeleted()).thenReturn(List.of(user2));

        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/admin/dogs/delete/{id}", "1")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users",List.of(user2)));

    }
}