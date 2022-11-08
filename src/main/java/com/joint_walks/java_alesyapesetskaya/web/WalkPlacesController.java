package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/dogwalker")
public class WalkPlacesController extends AbstractPlaceController {

    @GetMapping("/user/places")
    public String getForUser(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndLoggedUserAddToModel(model,"allCities",userSecurity,"loggedUser");
        return "walkPlaces";
    }

    @GetMapping("/user/places/{city}")
    public String getPlacesByCityForUser(@PathVariable @RequestParam("selected_city") String city,
                                  Model model,
                                  @AuthenticationPrincipal UserSecurity userSecurity) {
        filterPlacesByCityAndAddToModel(city,model,"allPlaces");
        getAllCitiesAndLoggedUserAddToModel(model,"allCities",userSecurity,"loggedUser");
        return "walkPlaces";
    }

    @PostMapping("/user/places/search")
    public String searchPlaceForUser(
            @RequestParam(name = "search_text") String text,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {
        getAllPlacesByPartialMatchAndAddToModel(text,model, "allPlaces");
        getAllCitiesAndLoggedUserAddToModel(model,"allCities",userSecurity,"loggedUser");
        return "walkPlaces";
    }

    @GetMapping("/admin/places")
    public String getForAdmin(Model model) {
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        return "walkPlacesAdmin";
    }

    @GetMapping("/admin/places/{city}")
    public String getPlacesByCityForAdmin(@PathVariable @RequestParam("selected_city") String city,
                                         Model model) {
        filterPlacesByCityAndAddToModel(city,model,"allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        return "walkPlacesAdmin";
    }

    @PostMapping("/admin/places/search")
    public String searchPlaceForAdmin(
            @RequestParam(name = "search_text") String text,
            Model model) {
        getAllPlacesByPartialMatchAndAddToModel(text,model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        return "walkPlacesAdmin";
    }

}
