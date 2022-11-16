package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void getAdminPage() throws Exception {
        UserDto user1 = UserDto.builder()
                .id(1L)
                .login("User")
                .isDeleted(false)
                .build();
        UserDto user2 = UserDto.builder()
                .id(2L)
                .login("New")
                .isDeleted(false)
                .build();

        List<UserDto> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

       Mockito.when(userService.getAllNotDeleted()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/main")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users",users));

    }
}