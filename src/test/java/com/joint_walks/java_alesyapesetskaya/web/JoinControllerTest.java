package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.junit.Before;
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
public class JoinControllerTest {

    @MockBean
    UserServiceImpl userService;

    @MockBean
    PlaceServiceImpl placeService;

    @MockBean
    AppointmentServiceImpl appointmentService;

    @Autowired
    private MockMvc mockMvc;

    private UserDto loggedUserDto;
    private User loggedUser;
    private List<AppointmentDto> allAppointmentsDtoWithoutUser;
    private List<String> allCities;
    private List<PlaceDto> allPlaces;

    @Before
    void setup() {
        loggedUserDto = UserDto.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();
        loggedUser = User.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();

        User user1 = User.builder()
                .id(2L)
                .login("User simple")
                .isDeleted(false)
                .build();
        List<User> allUsersWithoutLogged = new ArrayList<>();
        allUsersWithoutLogged.add(user1);


        Address addressMinsk = Address.builder()
                .city("Minsk")
                .build();
        Address addressGrodno = Address.builder()
                .city("Grodno")
                .build();
        allCities = List.of("Minsk", "Grodno");
        PlaceDto placeDtoMinsk = PlaceDto.builder()
                .id(1L)
                .address(addressMinsk)
                .build();
        PlaceDto placeDtoGrodno = PlaceDto.builder()
                .id(2L)
                .address(addressGrodno)
                .build();
        allPlaces = new ArrayList<>();
        AppointmentDto appointmentDto1 = AppointmentDto.builder()
                .id(1L)
                .users(allUsersWithoutLogged)
                .place(placeDtoGrodno)
                .build();
        AppointmentDto appointmentDto2 = AppointmentDto.builder()
                .id(2L)
                .place(placeDtoMinsk)
                .users(allUsersWithoutLogged)
                .build();
        allAppointmentsDtoWithoutUser.add(appointmentDto1);
        allAppointmentsDtoWithoutUser.add(appointmentDto2);
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
    }

    @Test
    void openForUser() throws Exception {
        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUserDto);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(loggedUser);
        Mockito.when(appointmentService.getAppointmentsWithoutUser(1L)).thenReturn(allAppointmentsDtoWithoutUser);
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);


        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/join/")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces", allPlaces))
                .andExpect(model().attribute("allCities", allCities))
                .andExpect(model().attribute("allAppointments", allAppointmentsDtoWithoutUser))
                .andExpect(model().attribute("loggedUser", loggedUserDto));
    }

    @Test
    void getPlacesByCityForUser() {
    }

    @Test
    void searchPlaceForUser() {
    }

    @Test
    void getWithSelectedAddressForUser() {
    }

    @Test
    void getForAdmin() {
    }

    @Test
    void getPlacesByCityForAdmin() {
    }

    @Test
    void searchPlaceForAdmin() {
    }

    @Test
    void deleteAppointment() {
    }
}