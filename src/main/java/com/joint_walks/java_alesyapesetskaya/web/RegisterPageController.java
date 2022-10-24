package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.model.Dog;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/register")
public class RegisterPageController extends AbstractUserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserMapperUtils converter;

    @GetMapping
    public String get(Model model) {

        return "register";
    }

    @PostMapping
    public String saveChanges(Model model,
                              @RequestParam(name = "ownerAge", required = false) Integer ownerAge,
                              @RequestParam(name = "dogName", required = false) String dogName,
                              @RequestParam(name = "dogType", required = false) String dogType,
                              @RequestParam(name = "dogAge", required = false) Integer dogAge) {


        return "success_register";
    }

}
