package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.ChangePersonalDataForm;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import com.joint_walks.java_alesyapesetskaya.validator.ImageFileValidatorForEdit;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidatorContext;
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
public class PersonalPageControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    ImageFileValidatorForEdit validator;

    @Autowired
    private MockMvc mockMvc;

    private final  UserDto loggedUserDto = UserDto.builder()
            .id(1L)
            .login("User")
            .age(33)
            .dog(new Dog())
            .isDeleted(false)
            .build();
    private List<Appointment> allAppointments;
    private  List<User> users;

    @BeforeEach
    void setup(){
        Mockito.when(userService.getUserDtoById(1L)).thenReturn(loggedUserDto);
        User user = User.builder()
                .id(1L)
                .login("New")
                .isDeleted(false)
                .build();
        users = new ArrayList<>();
        users.add(user);
        Address address = Address.builder()
                .city("Minsk")
                .street("New")
                .houseNumberNearby(1)
                .build();
        Place place = Place.builder()
                .address(address)
                .build();
        Appointment appointment1 = Appointment.builder()
                .id(1L)
                .users(users)
                .date(new Date(2022,10,10))
                .time(LocalTime.of(12, 0))
                .place(place)
                .build();
        Appointment appointment2 = Appointment.builder()
                .id(2L)
                .users(users)
                .build();
        allAppointments = new ArrayList<>();
        allAppointments.add(appointment1);
        allAppointments.add(appointment2);

    }

    @Test
    void openPersonalPage() throws Exception {
        Mockito.when(appointmentService.getAppointmentsByUserId(1L)).thenReturn(allAppointments);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/personalPage/1")
                        .with(user("User").password("User").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedUser", loggedUserDto))
                .andExpect(model().attribute("allUserAppointments",allAppointments));
    }

    @Test
    void leaveAppointment() throws Exception {
        Address address = Address.builder()
                .city("Minsk")
                .street("New")
                .houseNumberNearby(1)
                .build();
        Place place = Place.builder()
                .address(address)
                .build();
        Appointment appointment1 = Appointment.builder()
                .id(1L)
                .users(users)
                .date(new Date(2022,10,10))
                .time(LocalTime.of(12, 0))
                .place(place)
                .build();
        Mockito.when(appointmentService.getById(1L)).thenReturn(appointment1);
        Mockito.doAnswer(invocation -> {
            allAppointments.remove(appointment1);
            return null;
        }).when(appointmentService).deleteUserFromOneAppointment(1L, 1L);

        Mockito.when(appointmentService.getAppointmentsByUserId(1L)).thenReturn(allAppointments);

        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/personalPage/1/leaveAppointment/1")
                        .with(user("User").password("User").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedUser",loggedUserDto))
                .andExpect(model().attribute("allUserAppointments",allAppointments))
                .andExpect(model().attribute("success","You've successfully left the appointment. Date: 10-11-3922. Time: 12:00. Address: Minsk, New str. 1"));
    }

    @Test
    void openChangeDataPage() throws Exception {
        ChangePersonalDataForm personalDataForm = new ChangePersonalDataForm();
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/personalPage/1/changeData/")
                        .with(user("User").password("User").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loggedUser", loggedUserDto))
                .andExpect(model().attribute("changePersonalDataForm",personalDataForm));
    }

}