package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/places")
public class WalkPlacesController {

    @Autowired
    PlaceServiceImpl placeService;

    @GetMapping
    public String get(Model model) {
        List<Place> allPlaces = placeService.getAll();
        List<String> allCities = placeService.getAllCities();
        model.addAttribute("allPlaces", allPlaces);
        model.addAttribute("allCities", allCities);
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

    @PostMapping
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
