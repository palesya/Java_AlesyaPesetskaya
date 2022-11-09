package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/dogwalker")
public class JoinController extends AbstractAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/user/join")
    public String getForUser(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getAppointmentsWithoutUserAndAddToModel(model,"allAppointments",userSecurity);
        getAllCitiesAndAddToModel(model, "allCities");
        addAllPlacesAndLoggedUserToModel(model,"allPlaces",userSecurity,"loggedUser");
        return "join";
    }

    @GetMapping("/user/join/{city}")
    public String getPlacesByCityForUser(@PathVariable @RequestParam("selected_city") String city,
                                  Model model,
                                  @AuthenticationPrincipal UserSecurity userSecurity) {
        filterAppointmentsByCityWithoutUserAndAddToModel(city,model,"allAppointments",userSecurity);
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @PostMapping("/user/join/search")
    public String searchPlaceForUser(
            @RequestParam(name = "search_text") String text,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {
        getAppointmentsByPartialMatchWithoutUserAndAddToModel(model,text,"allAppointments", userSecurity);
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @PostMapping("/user/join/selected/{id}")
    public String getWithSelectedAddressForUser(@PathVariable Long id,
                                         Model model,
                                         @AuthenticationPrincipal UserSecurity userSecurity) {
        joinAppointmentAndAddItToModel(model,id,userSecurity);
        getAppointmentsWithoutUserAndAddToModel(model,"allAppointments",userSecurity);
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "join";
    }

    @GetMapping("/admin/appointment")
    public String getForAdmin(Model model) {
        getAllAppointmentsAndAddToModel(model,"allAppointments");
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        return "appointmentsAdmin";
    }

    @GetMapping("/admin/appointment/{city}")
    public String getPlacesByCityForAdmin(@PathVariable @RequestParam("selected_city") String city,
                                         Model model) {
        filterAppointmentsByCityAndAddToModel(city,model,"allAppointments");
        getAllCitiesAndAddToModel(model, "allCities");
        return "appointmentsAdmin";
    }

    @PostMapping("/admin/appointment/search")
    public String searchPlaceForAdmin(
            @RequestParam(name = "search_text") String text,
            Model model) {
        getAppointmentsByPartialMatchAndAddToModel(model,text,"allAppointments");
        getAllCitiesAndAddToModel(model, "allCities");
        return "appointmentsAdmin";
    }

    @PostMapping("/admin/appointment/delete/{id}")
    public String deleteAppointment(@PathVariable Long id,
                                    Model model){
        appointmentService.deleteAppointment(id);
        getAllAppointmentsAndAddToModel(model,"allAppointments");
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        return "appointmentsAdmin";
    }
}
