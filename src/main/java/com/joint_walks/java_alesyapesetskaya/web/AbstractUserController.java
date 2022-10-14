package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public abstract class AbstractUserController {

    @Autowired
    private UserServiceImpl userService;

    public void getLoggedUserByIdAndAddToModel(Long id, Model model, String attributeName) {
        UserDto userDto = userService.getUserDtoById(id);
        model.addAttribute(attributeName, userDto);
    }

    public void getUsersByPartialMatchAndAddToModel(String text, Model model, String attributeName) {
        List<UserDto> usersDto = userService.getUsersByPartialMatch(text);
        model.addAttribute(attributeName, usersDto);
    }

    public void getNotDeletedUsersAndAddToModel(Model model, String attributeName) {
        List<UserDto> allUsers = userService.getAllNotDeleted();
        model.addAttribute(attributeName, allUsers);
    }

    public void getLoggedUserByUserSecurityLoginAndAddToModel(@AuthenticationPrincipal UserSecurity userSecurity,
                                                              Model model,
                                                              String attributeName) {
        String securityUserLogin = userSecurity.getUsername();
        UserDto userDto = userService.getByLogin(securityUserLogin);
        model.addAttribute(attributeName, userDto);
    }

}
