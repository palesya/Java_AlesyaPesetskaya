package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(Suite.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    void openLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void forbidOpenAdminPageForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/main")
                        .with(user("User").password("User").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void forbidOpenUserPageForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/main")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    void openAdminPageForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/places")
                        .with(user("Admin").password("Admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void openUserPageForUser() throws Exception {
        UserSecurity userDetails = new UserSecurity("Alesya","pass1",List.of("ROLE_USER"),true);
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/dogs/")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void redirectToLoginWhenNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/dogs"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}