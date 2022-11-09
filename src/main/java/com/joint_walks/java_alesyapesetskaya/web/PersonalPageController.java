package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.Dog;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(path = "/dogwalker/user/personalPage")
public class PersonalPageController extends AbstractUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        getLoggedUserByIdAndAddToModel(id, model, "loggedUser");
        List<Appointment> appointmentsByUserId = appointmentService.getAppointmentsByUserId(id);
        model.addAttribute("allUserAppointments", appointmentsByUserId);
        return "personalPage";
    }

    @PostMapping("/{id}/changeData")
    public String getChanges(@PathVariable Long id, Model model) {
        getLoggedUserByIdAndAddToModel(id, model, "loggedUser");
        return "changeData";
    }

    @PostMapping("/{id}/savedChanges")
    public String saveChanges(@PathVariable Long id,
                              Model model,
                              @RequestParam(name = "ownerAge", required = false) Integer ownerAge,
                              @RequestParam(name = "dogName", required = false) String dogName,
                              @RequestParam(name = "dogType", required = false) String dogType,
                              @RequestParam(name = "dogAge", required = false) Integer dogAge) {

        User userFromDB = userService.getUserById(id);
        if (ownerAge != null && ownerAge > 0) {
            userFromDB.setAge(ownerAge);
        }
        if (dogName != null && !dogName.isBlank()) {
            Dog dog = userFromDB.getDog();
            dog.setName(dogName);
        }
        if (dogType != null && !dogType.isBlank()) {
            Dog dog = userFromDB.getDog();
            dog.setType(dogType);
        }
        if (dogAge != null && dogAge >= 0) {
            Dog dog = userFromDB.getDog();
            dog.setAge(dogAge);
        }
        userFromDB.setId(id);
        userService.saveUser(userFromDB);
        getLoggedUserByIdAndAddToModel(id, model, "loggedUser");
        return "personalPage";
    }

    @PostMapping("/{userId}/leaveAppointment/{appointmentId}")
    public String leaveAppointment(@PathVariable Long userId, Model model, @PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.getById(appointmentId);
        appointmentService.deleteUserFromOneAppointment(userId, appointmentId);
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(appointment.getDate());
        model.addAttribute("success", "You've successfully left the appointment. Date: " + date + ". Time: " + appointment.getTime() + ". Address: " + appointment.getPlace().getAddress());
        getLoggedUserByIdAndAddToModel(userId, model, "loggedUser");
        return "personalPage";
    }

}
