package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.exception.UserIsAlreadyAddedException;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppControllerAdvice extends AbstractAppointmentController{

    @ExceptionHandler(UserIsAlreadyAddedException.class)
    public String processException(UserIsAlreadyAddedException ex,
                                   Model model,
                                   @AuthenticationPrincipal UserSecurity userSecurity){
        String message = ex.getMessage();
        model.addAttribute("error",message);
        getAllAppointmentsAndAddToModel(model,"allAppointments");
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");

        return "join";
    }


}
