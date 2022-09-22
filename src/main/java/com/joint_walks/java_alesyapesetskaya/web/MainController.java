package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/dogwalker/main")
public class MainController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public String get(Model model) {
        List<User> allUsers = userService.getAllNotDeleted();
        model.addAttribute("users", allUsers);
        System.out.println("");
        return "main";
    }

    @PostMapping
    public String changeAvailability(Model model) {
        List<User> allUsers = userService.getAllNotDeleted();
        model.addAttribute("users", allUsers);
        return "main";
    }

}
