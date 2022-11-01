package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/dogwalker/join")
public class JoinController extends AbstractAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String get(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {

        getAllAppointmentsAndAddToModel(model,"allAppointments");
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @GetMapping("/{city}")
    public String getPlacesByCity(@PathVariable @RequestParam("selected_city") String city,
                                  Model model,
                                  @AuthenticationPrincipal UserSecurity userSecurity) {
        List<AppointmentDto> allAppointments;
        if (Objects.equals(city, "All cities")) {
            allAppointments = appointmentService.getAll();
        } else {
            allAppointments = appointmentService.getAppointmentByCity(city);
        }
        model.addAttribute("allAppointments", allAppointments);

        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @PostMapping("/search")
    public String searchPlace(
            @RequestParam(name = "search_text") String text,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {

        List<AppointmentDto> appointmentsByPartialMatch = appointmentService.getAppointmentsByPartialMatch(text);
        model.addAttribute("allAppointments", appointmentsByPartialMatch);

        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @PostMapping("/selected/{id}")
    public String getWithSelectedAddress(@PathVariable Long id,
                                         Model model,
                                         @AuthenticationPrincipal UserSecurity userSecurity) {
        User userByLogin = getUserByLoginFromUserSecurity(userSecurity);

        Appointment appointment = appointmentService.getById(id);
        Address address = appointment.getPlace().getAddress();
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(appointment.getDate());
        LocalTime time = appointment.getTime();

        appointmentService.joinAppointment(appointment,userByLogin);
        model.addAttribute("success", "You've been successfully added to the appointment. Date: "+date+". Time: "+time+". Address: "+address);

        getAllAppointmentsAndAddToModel(model,"allAppointments");
        getAllPlacesAndAddToModel(model,"allPlaces");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");
        return "join";
    }

}
