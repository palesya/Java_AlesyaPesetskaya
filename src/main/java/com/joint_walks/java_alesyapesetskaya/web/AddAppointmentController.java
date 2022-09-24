package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/dogwalker/add")
public class AddAppointmentController {

    @Autowired
    PlaceServiceImpl placeService;

    @PostMapping("/{id}")
    public String get(@PathVariable Long id,Model model) {
        Place place = placeService.getById(id);
        model.addAttribute("place", place);
        return "addNew";
    }

}
