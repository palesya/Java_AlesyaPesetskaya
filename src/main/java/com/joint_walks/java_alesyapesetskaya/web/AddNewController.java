package com.joint_walks.java_alesyapesetskaya.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dogwalker/add")
public class AddNewController {

    @GetMapping
    public String get() {
        return "addNew";
    }
}
