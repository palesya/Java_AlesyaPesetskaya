package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/register")
public class RegisterPageController extends AbstractUserController {

    @GetMapping
    public String get(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping
    public String saveChanges(@Valid @ModelAttribute(name = "registerForm") RegisterForm registerForm,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        saveNewUser(registerForm);
        return "success_register";

    }

}
