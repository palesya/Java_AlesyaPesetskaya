package com.joint_walks.java_alesyapesetskaya.config;


import com.joint_walks.java_alesyapesetskaya.model.Role;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TestUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Role role = new Role("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);
        Role roleAdmin = new Role("ROLE_ADMIN");
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(roleAdmin);
        User user = User.builder()
                .id(1L)
                .login("User")
                .password("User")
                .roles(userRoles)
                .isDeleted(false)
                .build();
        User admin = User.builder()
                .id(1L)
                .login("Admin")
                .password("Admin")
                .roles(adminRoles)
                .isDeleted(false)
                .build();
        User finalUser = null;
        if(Objects.equals(username, "User")){
            finalUser = user;
        }else if(Objects.equals(username, "Admin")){
            finalUser=admin;
        }
        List<Role> roles = finalUser.getRoles();
        List<String> rolesAsString = roles.stream().map(Role::getName).collect(Collectors.toList());
        boolean isEnabled = !finalUser.isDeleted();
        return new UserSecurity(user.getLogin(), user.getPassword(), rolesAsString, isEnabled);
    }
}
