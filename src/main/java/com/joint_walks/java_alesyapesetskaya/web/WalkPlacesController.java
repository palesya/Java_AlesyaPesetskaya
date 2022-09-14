package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/places")
public class WalkPlacesController {

    @Autowired
    PlaceServiceImpl placeService;

    @GetMapping
    public String get(Model model){
        List<Place> allPlaces = placeService.getAll();
        List<String> allCities = placeService.getAllCities();
        model.addAttribute("allPlaces",allPlaces);
        model.addAttribute("allCities",allCities);
        return "walkPlaces";
    }


}
