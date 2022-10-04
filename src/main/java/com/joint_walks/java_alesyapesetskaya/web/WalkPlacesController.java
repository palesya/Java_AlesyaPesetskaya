package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/dogwalker/places")
public class WalkPlacesController {

    @Autowired
    private PlaceServiceImpl placeService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String get(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        List<Place> allPlaces = placeService.getAll();
        List<String> allCities = placeService.getAllCities();
        model.addAttribute("allPlaces", allPlaces);
        model.addAttribute("allCities", allCities);

        String securityUserLogin = userSecurity.getUsername();
        User byLogin = userService.getByLogin(securityUserLogin);
        model.addAttribute("loggedUser", byLogin);
        return "walkPlaces";
    }

    @GetMapping("/{city}")
    public String getPlacesByCity(@PathVariable @RequestParam("selected_city") String city,
                                  Model model) {
        List<Place> allPlaces;
        if (Objects.equals(city, "All cities")) {
            allPlaces = placeService.getAll();
        } else {
            allPlaces = placeService.getPlacesByCity(city);
        }
        model.addAttribute("allPlaces", allPlaces);
        List<String> allCities = placeService.getAllCities();
        model.addAttribute("allCities", allCities);
        return "walkPlaces";
    }

    @PostMapping("/search")
    public String searchPlace(
            @RequestParam(name = "search_text") String text,
            Model model) {
        List<Place> allPlaces = placeService.getPlacesByPartialMatch(text);
        model.addAttribute("allPlaces", allPlaces);
        List<String> allCities = placeService.getAllCities();
        model.addAttribute("allCities", allCities);
        return "walkPlaces";
    }


}
