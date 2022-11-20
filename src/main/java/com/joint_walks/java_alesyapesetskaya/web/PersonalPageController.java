package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.ChangePersonalDataForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/dogwalker/user/personalPage")
public class PersonalPageController extends AbstractAppointmentController {

    @GetMapping("/{userId}")
    public String get(@PathVariable Long userId, Model model) {
        getLoggedUserAndItsAppointmentsAndAddToModel(model,userId,"loggedUser","allUserAppointments");
        return "personalPage";
    }

    @PostMapping("/{id}/changeData")
    public String getChanges(@PathVariable Long id, Model model) {
        model.addAttribute("changePersonalDataForm",new ChangePersonalDataForm());
        getLoggedUserByIdAndAddToModel(id, model, "loggedUser");
        return "changeData";
    }

    @PostMapping("/{userId}/savedChanges")
    public String saveChanges(@PathVariable Long userId,
                              Model model,
                              @Valid @ModelAttribute(name = "changePersonalDataForm") ChangePersonalDataForm form,
                              BindingResult bindingResult) throws IOException {
        getLoggedUserAndItsAppointmentsAndAddToModel(model,userId,"loggedUser","allUserAppointments");
        if (bindingResult.hasErrors()) {
            return "changeData";
        }
        saveUserChanges(userId,form);
        getLoggedUserAndItsAppointmentsAndAddToModel(model,userId,"loggedUser","allUserAppointments");
        return "personalPage";
    }

    @PostMapping("/{userId}/leaveAppointment/{appointmentId}")
    public String leaveAppointment(@PathVariable Long userId, Model model, @PathVariable Long appointmentId) {
        leaveAppointmentAndAddSuccessMessageToModel(model,appointmentId,userId,"success");
        getLoggedUserAndItsAppointmentsAndAddToModel(model,userId,"loggedUser","allUserAppointments");
        return "personalPage";
    }

}
