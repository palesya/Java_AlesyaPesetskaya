package com.joint_walks.java_alesyapesetskaya.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/add")
public class AddNewController {

    @GetMapping
    public String get(Model model) {

        return "addNew";
    }
}
