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
        model.addAttribute("appointment", new Appointment());
        return "addNew";
    }

    @PostMapping("/addWithSelectedPlace/{id}")
    public String getWithSelectedAddress(@PathVariable Long id, Model model) {
        Place place = placeService.getById(id);
        model.addAttribute("selected_place", place);
        List<Place> allPlaces = placeService.getAll();
        model.addAttribute("allPlaces", allPlaces);
        return "addNew";
    }

    @PostMapping("/addAddress/added")
    public String getCreatedAppointment(
            @RequestParam("selected_address") String address,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("description") String description,
            Model model) {
        model.addAttribute("added_address", address);
        model.addAttribute("added_date", date);
        model.addAttribute("added_time", time);
        model.addAttribute("added_description", description);
        return "addedNew";
    }


}
