package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Controller
@RequestMapping(path = "/dogwalker/user")
public class AddAppointmentController extends AbstractPlaceController{

    @Autowired
    private PlaceService placeService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/add")
    public String get(Model model,
                      @AuthenticationPrincipal UserSecurity userSecurity) {
        getAllPlacesAndAddToModel(model,"allPlaces");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");
        return "addNew";
    }

    @PostMapping("/addWithSelectedPlace/{id}")
    public String getWithSelectedAddress(@PathVariable Long id,
                                         Model model,
                                         @AuthenticationPrincipal UserSecurity userSecurity) {
        getPlaceByIdAndAddToModel(id,model,"selected_place");
        getAllPlacesAndAddToModel(model,"allPlaces");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");
        return "addNew";
    }

    @PostMapping("/addAddress/added")
    public String getCreatedAppointment(
            @RequestParam("selected_address") Address address,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
            @RequestParam("description") String description,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {
        model.addAttribute("added_address", address);
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formattedDate = simpleDateFormat.format(date);
        model.addAttribute("added_date", formattedDate);
        model.addAttribute("added_time", time);
        model.addAttribute("added_description", description);
        Place placeByAddress = placeService.getPlaceByAddress(address);

        User userByLogin = getUserByLoginFromUserSecurity(userSecurity);

        appointmentService.createAppointment(new Appointment(placeByAddress,date,time,description),userByLogin);
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");
        return "addedNew";
    }

}
