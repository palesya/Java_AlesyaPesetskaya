package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dogwalker")
public class MainController extends AbstractUserController {

    @GetMapping("/user/main")
    public String getUserPage(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {

        getNotDeletedUsersAndAddToModel(model, "users");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");

        return "main";
    }

    @GetMapping("/admin/main")
    public String getAdminPage(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getNotDeletedUsersAndAddToModel(model, "users");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");
        return "mainAdmin";
    }

}
