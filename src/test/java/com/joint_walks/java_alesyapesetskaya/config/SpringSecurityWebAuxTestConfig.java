package com.joint_walks.java_alesyapesetskaya.config;

import com.joint_walks.java_alesyapesetskaya.model.Role;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.RoleService;
import com.joint_walks.java_alesyapesetskaya.service.UserDetailsServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @MockBean
    private UserService userService;

    @MockBean
    private PlaceServiceImpl placeService;

    @MockBean
    private RoleService roleService;

//    @MockBean
//    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        List<Role> rolesUser = List.of(userRole);
        List<Role> rolesAdmin = List.of(adminRole);
        List<String> rolesForUserAsString = rolesUser.stream().map(Role::getName).collect(Collectors.toList());
        List<String> rolesForAdminAsString = rolesAdmin.stream().map(Role::getName).collect(Collectors.toList());
        UserSecurity simpleUser = new UserSecurity("User", "User", rolesForUserAsString, true);
        UserSecurity admin = new UserSecurity("Admin", "Admin", rolesForAdminAsString, true);
        UserSecurity disabledUser = new UserSecurity("Disabled", "Disabled", rolesForUserAsString, false);

        return new InMemoryUserDetailsManager(Arrays.asList(
                simpleUser, admin, disabledUser
        ));
    }

}
