package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.config.LoginSuccessHandler;
import com.joint_walks.java_alesyapesetskaya.dto.RegisterForm;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.RoleService;
import com.joint_walks.java_alesyapesetskaya.service.UserDetailsServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = RegisterPageController.class)
class RegisterPageControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private PlaceServiceImpl placeService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void openRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register/"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("registerForm", instanceOf(RegisterForm.class)));
    }

    @Test
    void saveChanges() {
    }
}