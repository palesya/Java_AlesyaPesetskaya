package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/dogwalker/user/places")
public class WalkPlacesController extends AbstractPlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public String get(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getAllPlacesAndAddToModel(model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "walkPlaces";
    }

    @GetMapping("/{city}")
    public String getPlacesByCity(@PathVariable @RequestParam("selected_city") String city,
                                  Model model,
                                  @AuthenticationPrincipal UserSecurity userSecurity) {
        List<PlaceDto> allPlaces;
        if (Objects.equals(city, "All cities")) {
            allPlaces = placeService.getAll();
        } else {
            allPlaces = placeService.getPlacesByCity(city);
        }
        model.addAttribute("allPlaces", allPlaces);

        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "walkPlaces";
    }

    @PostMapping("/search")
    public String searchPlace(
            @RequestParam(name = "search_text") String text,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {
        getAllPlacesByPartialMatchAndAddToModel(text,model, "allPlaces");
        getAllCitiesAndAddToModel(model, "allCities");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "walkPlaces";
    }

}
