package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dogwalker")
public class AppointmentInfoController extends AbstractAppointmentController{

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserService userService;

    @GetMapping("/user/appointment/{id}")
    public String get(@PathVariable Long id, Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        Appointment appointment = appointmentService.getById(id);
        model.addAttribute("appointment", appointment);
        getUsersFromAppointmentAndAddToModel(model,"allUsers",id);
        boolean isUserAdded = isUserAddedToAppointment(userSecurity, id);
        model.addAttribute("isUserAdded", isUserAdded);
        return "appointmentInfo";
    }

}
