package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/dogwalker/join")
public class JoinController extends AbstractPlaceController {

    @Autowired
    private PlaceService placeService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public String get(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        List<Appointment> all = appointmentService.getAll();
        model.addAttribute("allAppointments", all);

        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @GetMapping("/{city}")
    public String getPlacesByCity(@PathVariable @RequestParam("selected_city") String city,
                                  Model model,
                                  @AuthenticationPrincipal UserSecurity userSecurity) {
        List<Appointment> allAppointments;
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

        List<Appointment> appointmentsByPartialMatch = appointmentService.getAppointmentsByPartialMatch(text);
        model.addAttribute("allAppointments", appointmentsByPartialMatch);

        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @PostMapping("/selected/{id}")
    public String getWithSelectedAddress(@PathVariable Long id,
                                         Model model,
                                         @AuthenticationPrincipal UserSecurity userSecurity) {
        Appointment selected_appointment = appointmentService.getById(id);
        Integer numberOfPeople = selected_appointment.getNumberOfPeople();
        selected_appointment.setNumberOfPeople(numberOfPeople+1);
        appointmentService.saveAppointment(selected_appointment);
        List<Appointment> all = appointmentService.getAll();
        model.addAttribute("allAppointments", all);


        getAllPlacesAndAddToModel(model,"allPlaces");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,"loggedUser");
        return "join";
    }

}
