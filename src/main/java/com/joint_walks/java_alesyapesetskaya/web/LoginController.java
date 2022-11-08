package com.joint_walks.java_alesyapesetskaya.web;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                String errorMessage;
                if (ex instanceof DisabledException) {
                    errorMessage = "Such user is disabled.";
                } else {
                    errorMessage = "Invalid login and/or password";
                }
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        return "login";
    }
}
