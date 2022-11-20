package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

    private final UserDto loggedUserDto = UserDto.builder()
            .id(1L)
            .login("User")
            .isDeleted(false)
            .build();;
    private final User loggedUser = User.builder()
            .id(1L)
            .login("User")
            .password("User")
            .isDeleted(false)
            .build();
    private final UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
    private List<AppointmentDto> allAppointmentsDtoWithoutUser;
    private List<AppointmentDto> allAppointmentsDto;
    private List<AppointmentDto> allAppointmentsMinskWithoutUser;
    private List<AppointmentDto> allAppointmentsMinsk;
    private List<String> allCities;
    private List<PlaceDto> allPlaces;
    private AppointmentDto appointmentToAddDto;

    @BeforeEach
    void setup() {
        allAppointmentsDto = new ArrayList<>();
        allAppointmentsDtoWithoutUser = new ArrayList<>();
        allAppointmentsMinskWithoutUser = new ArrayList<>();
        allAppointmentsMinsk = new ArrayList<>();
        allPlaces = new ArrayList<>();
        allCities = new ArrayList<>();
        User user1 = User.builder()
                .id(2L)
                .login("User simple")
                .isDeleted(false)
                .build();
        User loggedUser = User.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();
        List<User> allUsers = new ArrayList<>();
        allUsers.add(loggedUser);
        allUsers.add(user1);
        Address addressMinsk = Address.builder()
                .id(1L)
                .city("Minsk")
                .build();
        Address addressGrodno = Address.builder()
                .id(2L)
                .city("Grodno")
                .street("New")
                .houseNumberNearby(3)
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
        List<User> allUsersWithoutLogged = new ArrayList<>();
        appointmentToAddDto = AppointmentDto.builder()
                .id(1L)
                .users(allUsersWithoutLogged)
                .place(placeDtoGrodno)
                .build();
        AppointmentDto appointmentDto2 = AppointmentDto.builder()
                .id(2L)
                .place(placeDtoMinsk)
                .users(allUsersWithoutLogged)
                .build();
        AppointmentDto appointmentDto3 = AppointmentDto.builder()
                .id(3L)
                .place(placeDtoMinsk)
                .users(allUsers)
                .build();
        allAppointmentsDtoWithoutUser.add(appointmentToAddDto);
        allAppointmentsDtoWithoutUser.add(appointmentDto2);
        allAppointmentsMinskWithoutUser.add(appointmentDto2);
        allAppointmentsMinsk.add(appointmentDto2);
        allAppointmentsMinsk.add(appointmentDto3);
        allAppointmentsDto.add(appointmentToAddDto);
        allAppointmentsDto.add(appointmentDto2);
        allAppointmentsDto.add(appointmentDto3);
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(loggedUser);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUserDto);
    }

    @Test
    void openForUser() throws Exception {
        Mockito.when(appointmentService.getAppointmentsWithoutUser(1L)).thenReturn(allAppointmentsDtoWithoutUser);
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);

        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/join/")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCities", allCities))
                .andExpect(model().attribute("allAppointments", allAppointmentsDtoWithoutUser))
                .andExpect(model().attribute("loggedUser", loggedUserDto));
    }

    @Test
    void searchPlacesByCityForUser() throws Exception {
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(appointmentService.getAppointmentsByCity("Minsk")).thenReturn(allAppointmentsMinsk);
        Mockito.when(appointmentService.excludeAppointmentsWithUser(1L, allAppointmentsMinsk)).thenReturn(allAppointmentsMinskWithoutUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/join/city?selected_city=Minsk")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCities", allCities))
                .andExpect(model().attribute("allAppointments", allAppointmentsMinskWithoutUser))
                .andExpect(model().attribute("loggedUser", loggedUserDto));
    }

    @Test
    void searchPlacesByAllCitiesForUser() throws Exception {
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(appointmentService.getAppointmentsWithoutUser(1L)).thenReturn(allAppointmentsDtoWithoutUser);
        Mockito.when(appointmentService.getAppointmentsByCity("Minsk")).thenReturn(allAppointmentsDtoWithoutUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/join/city?selected_city=All cities")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCities", allCities))
                .andExpect(model().attribute("allAppointments", allAppointmentsDtoWithoutUser))
                .andExpect(model().attribute("loggedUser", loggedUserDto));
    }

    @Test
    void searchPlaceForUser() {
    }

    @Test
    void joinAppointment() throws Exception {
        Address addressGrodno = Address.builder()
                .id(2L)
                .city("Grodno")
                .street("New")
                .houseNumberNearby(3)
                .build();
        Place placeGrodno = Place.builder()
                .id(2L)
                .address(addressGrodno)
                .build();
        List<User> allUsersWithoutLogged = new ArrayList<>();
        User user1 = User.builder()
                .id(2L)
                .login("Simple")
                .build();
        allUsersWithoutLogged.add(user1);
        Appointment appointmentToAdd = Appointment.builder()
                .id(1L)
                .users(allUsersWithoutLogged)
                .time(LocalTime.of(20, 20))
                .date(new Date(2022, 12, 12))
                .place(placeGrodno)
                .build();
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(appointmentService.getById(1L)).thenReturn(appointmentToAdd);
        Mockito.when(appointmentService.getAppointmentsWithoutUser(1L)).thenReturn(allAppointmentsDtoWithoutUser);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(loggedUser);
        Mockito.doAnswer(invocation -> {
                    List<User> users = appointmentToAdd.getUsers();
                    users.add(loggedUser);
                    allAppointmentsDtoWithoutUser.remove(appointmentToAddDto);
                    return null;
                }
        ).when(appointmentService).joinAppointment(appointmentToAdd,loggedUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/join/selected/1")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCities", allCities))
                .andExpect(model().attribute("allAppointments", allAppointmentsDtoWithoutUser))
                .andExpect(model().attribute("loggedUser", loggedUserDto))
                .andExpect(model().attribute("success", "You've been successfully added to the appointment. Date: 12-01-3923. Time: 20:20. Address: Grodno, New str. 3"));
        Assertions.assertTrue(appointmentToAdd.getUsers().contains(loggedUser));
        Assertions.assertEquals(1, allAppointmentsDtoWithoutUser.size());
    }

    @Test
    void deleteAppointment() throws Exception {
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(appointmentService.getAll()).thenReturn(allAppointmentsDto);
        Mockito.doAnswer(invocation -> {
                    allAppointmentsDto.remove(appointmentToAddDto);
                    return null;
                }
        ).when(appointmentService).deleteAppointment(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/admin/appointment/delete/1")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCities", allCities))
                .andExpect(model().attribute("allAppointments", allAppointmentsDto));
        Assertions.assertEquals(2, allAppointmentsDto.size());
    }
}