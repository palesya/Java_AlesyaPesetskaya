package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/dogwalker/user")
public class AddAppointmentController extends AbstractAppointmentController {

    @GetMapping("/add")
    public String get(Model model,
                      @AuthenticationPrincipal UserSecurity userSecurity) {
        System.out.println();
        if (model.getAttribute("appointmentForm") == null) {
            model.addAttribute("appointmentForm", new AddAppointmentForm());
        }
        addAllPlacesAndLoggedUserToModel(model,"allPlaces",userSecurity,"loggedUser");
        return "addNew";
    }

    @PostMapping("/addWithSelectedPlace/{id}")
    public String createWithSelectedAddress(@PathVariable Long id,
                                         Model model,
                                         @AuthenticationPrincipal UserSecurity userSecurity) {
        model.addAttribute("appointmentForm", new AddAppointmentForm());
        getPlaceByIdAndAddToModel(id, model, "selected_place");
        addAllPlacesAndLoggedUserToModel(model,"allPlaces",userSecurity,"loggedUser");
        return "addNew";
    }

    @PostMapping("/addedNew")
    public String createAppointment(
            @Valid @ModelAttribute("appointmentForm") AddAppointmentForm appointmentForm,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {
        if (bindingResult.hasErrors()) {
            addAllPlacesAndLoggedUserToModel(model,"allPlaces",userSecurity,"loggedUser");
            showAddressIfItWasSelected(appointmentForm,model,"selected_place");
            return "addNew";
        } else {
            model.addAttribute("added_appointment",appointmentForm);
            createAppointment(appointmentForm,userSecurity);
            getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
            return "addedNew";
        }

    }
}
