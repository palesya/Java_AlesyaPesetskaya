package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.config.LoginSuccessHandler;
import com.joint_walks.java_alesyapesetskaya.config.SpringSecurityWebAuxTestConfig;
import com.joint_walks.java_alesyapesetskaya.model.Role;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.RoleService;
import com.joint_walks.java_alesyapesetskaya.service.UserDetailsServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Mock
    UserSecurity userSecurity;

    @Test
    void openLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails("User")
    void forbidOpenAdminPageForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/main"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Admin")
    void forbidOpenUserPageForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/main"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Admin")
    void openAdminPageForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/admin/places"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("User")
    void openUserPageForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/places"))
                .andExpect(status().isOk());
    }

    @Test
    public void redirectToLoginWhenNotAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogwalker/user/dogs"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}