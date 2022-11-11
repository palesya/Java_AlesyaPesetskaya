package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dogwalker")
public class AppointmentInfoController extends AbstractAppointmentController{

    @GetMapping("/user/appointment/{appointmentId}")
    public String get(@PathVariable Long appointmentId, Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        getAppointmentByIdAndAddToModel(model,appointmentId,"appointment");
        getUsersFromAppointmentAndAddToModel(model,appointmentId,"allUsers");
        checkIfUserIsAddedToAppointmentAndAddToModel(userSecurity,appointmentId,model,"isUserAdded");
        return "appointmentInfo";
    }

}
