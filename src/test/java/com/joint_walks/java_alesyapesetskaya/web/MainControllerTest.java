package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

//    @Test
//    void getAdminPage() {
//
//       Mockito userService.getAllNotDeleted()
//        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/main")
//                        .with(user("Admin").password("Admin").roles("ADMIN")))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("users",userDto))
//                .andExpect(model().attribute("allUserAppointments",allAppointments));
//
//    }
}