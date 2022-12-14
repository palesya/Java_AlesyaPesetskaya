package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
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
public class WalkPlacesControllerTest {

    @MockBean
    private PlaceServiceImpl placeService;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchByCityForAdmin() throws Exception {
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
        List<PlaceDto> allPlaces = new ArrayList<>();
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
        List<PlaceDto> placesMinsk = new ArrayList<>();
        placesMinsk.add(placeDtoMinsk);
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getPlacesByCity("Minsk")).thenReturn(placesMinsk);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/places/city?selected_city=Minsk")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces",placesMinsk));

    }

    @Test
    void searchByAllCitiesForAdmin() throws Exception {
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
        List<PlaceDto> allPlaces = new ArrayList<>();
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);

        Mockito.when(placeService.getAll()).thenReturn(allPlaces);

        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/places/city?selected_city=All cities")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces",allPlaces));
    }

    @Test
    void openForUser() throws Exception {
        UserDto loggedUser = UserDto.builder()
                .id(1L)
                .login("Amops")
                .isDeleted(false)
                .build();
        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);

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
        List<PlaceDto> allPlaces = new ArrayList<>();
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
        List<String> allCities = List.of("Minsk","Grodno");
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/places/")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces",allPlaces))
                .andExpect(model().attribute("allCities",allCities))
                .andExpect(model().attribute("loggedUser", loggedUser));
    }

    @Test
    void searchByMinskForUser() throws Exception {
        UserDto loggedUser = UserDto.builder()
                .id(1L)
                .login("Amops")
                .isDeleted(false)
                .build();
        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);

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
        List<PlaceDto> allPlaces = new ArrayList<>();
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
        List<PlaceDto> placesMinsk = new ArrayList<>();
        placesMinsk.add(placeDtoMinsk);
        List<String> allCities = List.of("Minsk","Grodno");
        Mockito.when(placeService.getPlacesByCity("Minsk")).thenReturn(placesMinsk);
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/places/city?selected_city=Minsk")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces", placesMinsk))
                .andExpect(model().attribute("allCities",allCities))
                .andExpect(model().attribute("loggedUser", loggedUser));
    }

    @Test
    void searchAllCitiesForUser() throws Exception {
        UserDto loggedUser = UserDto.builder()
                .id(1L)
                .login("Amops")
                .isDeleted(false)
                .build();
        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);

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
        List<PlaceDto> allPlaces = new ArrayList<>();
        allPlaces.add(placeDtoGrodno);
        allPlaces.add(placeDtoMinsk);
        List<String> allCities = List.of("Minsk","Grodno");
        Mockito.when(placeService.getAll()).thenReturn(allPlaces);
        Mockito.when(placeService.getAllCities()).thenReturn(allCities);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/places/city?selected_city=All cities")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces",allPlaces))
                .andExpect(model().attribute("allCities",allCities))
                .andExpect(model().attribute("loggedUser", loggedUser));
    }

}