package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.converter.StringToAddressConverter;
import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(Suite.class)
@SpringBootTest
@AutoConfigureMockMvc
@Execution(ExecutionMode.SAME_THREAD)
public class AddAppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceServiceImpl placeService;

    @Autowired
    private StringToAddressConverter converter;

    @Autowired
    private AppointmentRepository repository;

    @MockBean
    UserServiceImpl userService;

    private final UserDto loggedUserDto = UserDto.builder()
            .id(1L)
            .login("User")
            .isDeleted(false)
            .build();
    private final User loggedUser = User.builder()
            .id(1L)
            .login("User")
            .password("User")
            .isDeleted(false)
            .build();
    private final UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
    private List<PlaceDto> allPlaces;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        Address addressMinsk = Address.builder()
                .city("Minsk")
                .build();
        Address addressGrodno = Address.builder()
                .city("Grodno")
                .build();

        PlaceDto placeDtoMinsk = PlaceDto.builder()
                .id(1L)
                .address(addressMinsk)
                .build();
        PlaceDto placeDtoGrodno = PlaceDto.builder()
                .id(2L)
                .address(addressGrodno)
                .build();
        allPlaces = new ArrayList<>();
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
        Mockito.when(userService.getUserByLogin("User")).thenReturn(loggedUser);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUserDto);
    }

    @Test
    void openPage() throws Exception {
        AddAppointmentForm appointmentForm = new AddAppointmentForm();
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/add/")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("appointmentForm", appointmentForm))
                .andExpect(model().attribute("allPlaces", allPlaces))
                .andExpect(model().attribute("loggedUser", loggedUserDto));
    }

    @Test
    void createAppointmentValid() throws Exception {
        Address address = Address.builder()
                .street("Light")
                .city("London")
                .houseNumberNearby(1)
                .id(1L)
                .build();
        Place place = Place.builder()
                .id(1L)
                .address(address)
                .build();
        AddAppointmentForm appointmentForm = AddAppointmentForm.builder()
                .time(LocalTime.of(20, 20))
                .date(new Date(2022, 10, 10))
                .address(address)
                .build();
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getPlaceByAddress(address)).thenReturn(place);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/addedNew/")
                        .with(user(userDetails))
                        .flashAttr("appointmentForm", appointmentForm))
                .andExpect(status().isOk())
                .andExpect(view().name("addedNew"))
                .andExpect(model().attribute("added_appointment", appointmentForm));
    }

    @Test
    void createAppointmentWithEmptyAddress() throws Exception {
        Address address = Address.builder()
                .street("Light")
                .city("London")
                .houseNumberNearby(1)
                .id(1L)
                .build();
        Place place = Place.builder()
                .id(1L)
                .address(address)
                .build();
        AddAppointmentForm appointmentForm = AddAppointmentForm.builder()
                .time(LocalTime.of(20, 20))
                .date(new Date(2022, 10, 10))
                .address(null)
                .build();
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getPlaceByAddress(address)).thenReturn(place);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/addedNew/")
                        .with(user(userDetails))
                        .flashAttr("appointmentForm", appointmentForm))
                .andExpect(view().name("addNew"))
                .andExpect(model().attribute("allPlaces", allPlaces))
                .andExpect(model().attribute("loggedUser", loggedUserDto))
                .andExpect(model().attributeHasFieldErrors("appointmentForm","address"));
    }

    @Test
    void createAppointmentWithEmptyDateAndTime() throws Exception {
        Address address = Address.builder()
                .street("Light")
                .city("London")
                .houseNumberNearby(1)
                .id(1L)
                .build();
        Place place = Place.builder()
                .id(1L)
                .address(address)
                .build();
        AddAppointmentForm appointmentForm = AddAppointmentForm.builder()
                .time(null)
                .date(null)
                .address(address)
                .build();
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getPlaceByAddress(address)).thenReturn(place);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/addedNew/")
                        .with(user(userDetails))
                        .flashAttr("appointmentForm", appointmentForm))
                .andExpect(view().name("addNew"))
                .andExpect(model().attribute("allPlaces", allPlaces))
                .andExpect(model().attribute("loggedUser", loggedUserDto))
                .andExpect(model().attributeHasFieldErrors("appointmentForm","date","time"));
    }

    @Test
    void createAppointmentWithInvalidTime() throws Exception {
        Address address = Address.builder()
                .street("Light")
                .city("London")
                .houseNumberNearby(1)
                .id(1L)
                .build();
        Place place = Place.builder()
                .id(1L)
                .address(address)
                .build();
        AddAppointmentForm appointmentForm = AddAppointmentForm.builder()
                .time(LocalTime.of(20, 20))
                .date(new Date(2022, 10, 10))
                .address(address)
                .description("Get umbrellas")
                .build();
        AddAppointmentForm appointmentFormWithInvalidTime = AddAppointmentForm.builder()
                .time(LocalTime.of(21, 10))
                .date(new Date(2022, 10, 10))
                .address(address)
                .build();
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getPlaceByAddress(address)).thenReturn(place);
        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/addedNew/")
                        .with(user(userDetails))
                        .flashAttr("appointmentForm", appointmentForm))
                .andExpect(view().name("addedNew"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/dogwalker/user/addedNew/")
                        .with(user(userDetails))
                        .flashAttr("appointmentForm", appointmentFormWithInvalidTime))
                .andExpect(view().name("addNew"))
                .andExpect(model().attribute("error", "This time is busy by another appointment."));
    }
}