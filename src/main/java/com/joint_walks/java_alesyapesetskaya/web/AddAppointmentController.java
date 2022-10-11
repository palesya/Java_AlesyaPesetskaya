package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/dogwalker")
public class AddAppointmentController {

    @Autowired
    private PlaceServiceImpl placeService;

    @GetMapping("/add")
    public String get(Model model) {
        List<Place> allPlaces = placeService.getAll();
        model.addAttribute("allPlaces", allPlaces);
        return "addNew";
    }

    @PostMapping("/addWithSelectedPlace/{id}")
    public String get(@PathVariable Long id, Model model) {
        Place place = placeService.getById(id);
        model.addAttribute("selected_place", place);
        List<Place> allPlaces = placeService.getAll();
        model.addAttribute("allPlaces", allPlaces);
        return "addNew";
    }


}
