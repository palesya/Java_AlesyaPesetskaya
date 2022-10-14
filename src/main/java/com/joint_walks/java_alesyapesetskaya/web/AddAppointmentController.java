package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Controller
@RequestMapping(path = "/dogwalker")
public class AddAppointmentController extends AbstractPlaceController{

    @Autowired
    private PlaceServiceImpl placeService;

    @GetMapping("/add")
    public String get(Model model) {
        getAllPlacesAndAddToModel(model,"allPlaces");
        return "addNew";
    }

    @PostMapping("/addWithSelectedPlace/{id}")
    public String getWithSelectedAddress(@PathVariable Long id, Model model) {
        getPlaceByIdAndAddToModel(id,model,"selected_place");
        getAllPlacesAndAddToModel(model,"allPlaces");
        return "addNew";
    }

    @PostMapping("/addAddress/added")
    public String getCreatedAppointment(
            @RequestParam("selected_address") Address address,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
            @RequestParam("description") String description,
            Model model) {
        model.addAttribute("added_address", address);
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formattedDate = simpleDateFormat.format(date);
        model.addAttribute("added_date", formattedDate);
        model.addAttribute("added_time", time);
        model.addAttribute("added_description", description);
        placeService.createAppointment(address,date,time,description);
        return "addedNew";
    }

}
