package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.dto.ChangePersonalDataForm;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
        model.addAttribute("changePersonalDataForm",new ChangePersonalDataForm());
        getLoggedUserByIdAndAddToModel(id, model, "loggedUser");
        return "changeData";
    }

    @PostMapping("/{id}/savedChanges")
    public String saveChanges(@PathVariable Long id,
                              Model model,
                              @Valid @ModelAttribute(name = "changePersonalDataForm") ChangePersonalDataForm form,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            getLoggedUserByIdAndAddToModel(id, model, "loggedUser");
            return "changeData";
        }
        saveUserChanges(id,form);
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
