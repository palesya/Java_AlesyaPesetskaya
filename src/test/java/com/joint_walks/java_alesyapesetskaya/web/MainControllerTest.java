package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
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
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    private List<UserDto> usersDto = new ArrayList<>();
    private UserDto loggedUser;

    @Before
    void setup() {
        loggedUser = UserDto.builder()
                .id(1L)
                .login("Amops")
                .isDeleted(false)
                .build();
        UserDto user2 = UserDto.builder()
                .id(2L)
                .login("Opmops")
                .isDeleted(false)
                .build();
        usersDto = new ArrayList<>();
        usersDto.add(loggedUser);
        usersDto.add(user2);
    }

    @Test
    void openMainForAdmin() throws Exception {
        Mockito.when(userService.getAllNotDeleted()).thenReturn(usersDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/main")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", usersDto));

    }

    @Test
    void openMainForUser() throws Exception {
        UserSecurity userDetails = new UserSecurity("User", "User", List.of("ROLE_USER"), true);
        Mockito.when(userService.getAllNotDeleted()).thenReturn(usersDto);
        Mockito.when(userService.getUserDtoByLogin("User")).thenReturn(loggedUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/main/")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", usersDto))
                .andExpect(model().attribute("loggedUser", loggedUser));

    }
}