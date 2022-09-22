package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
@RequestMapping(path = "/dogwalker/dogs")
public class DogsController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public String get(Model model) {
        List<User> allUsers = userService.getAllNotDeleted();
        model.addAttribute("users", allUsers);
        return "dogs";
    }

    @PostMapping
    public String searchDog(
            @RequestParam(name = "search_text") String text,
            Model model){
        List<User> allUsers = userService.getUsersByPartialMatch(text);
        model.addAttribute("users", allUsers);
        return "dogs";
    }
}
