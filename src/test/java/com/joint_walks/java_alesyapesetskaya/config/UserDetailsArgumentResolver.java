package com.joint_walks.java_alesyapesetskaya.config;

import com.joint_walks.java_alesyapesetskaya.model.Role;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserSecurity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Role role = new Role("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);
        User user = User.builder()
                .id(1L)
                .login("User")
                .password("User")
                .roles(userRoles)
                .isDeleted(false)
                .build();
        List<Role> roles = user.getRoles();
        List<String> rolesAsString = roles.stream().map(Role::getName).collect(Collectors.toList());
        boolean isEnabled = !user.isDeleted();
        return new UserSecurity(user.getLogin(), user.getPassword(), rolesAsString, isEnabled);
    }
}
