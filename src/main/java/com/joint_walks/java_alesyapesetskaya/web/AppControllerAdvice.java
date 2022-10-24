package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.exception.UserIsAlreadyAddedException;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class AppControllerAdvice extends AbstractPlaceController{

    @Autowired
    private AppointmentService appointmentService;

    @ExceptionHandler(UserIsAlreadyAddedException.class)
    public String processException(UserIsAlreadyAddedException ex,
                                   Model model,
                                   @AuthenticationPrincipal UserSecurity userSecurity){
        String message = ex.getMessage();
        model.addAttribute("error",message);
        List<AppointmentDto> all = appointmentService.getAll();
        model.addAttribute("allAppointments", all);

        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");

        return "join";
    }


}
