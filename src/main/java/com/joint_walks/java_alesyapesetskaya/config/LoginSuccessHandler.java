package com.joint_walks.java_alesyapesetskaya.config;

import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserSecurity userDetails = (UserSecurity) authentication.getPrincipal();
        String redirectURL = request.getContextPath();

        if (userDetails.hasRole("ROLE_ADMIN")) {
            redirectURL = "/dogwalker/admin/main";
        } else if (userDetails.hasRole("ROLE_USER")) {
            redirectURL = "/dogwalker/user/main";
        }
        response.sendRedirect(redirectURL);
    }

}
