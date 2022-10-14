package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public abstract class AbstractPlaceController extends AbstractUserController{

    @Autowired
    private PlaceServiceImpl placeService;

    public void getAllPlacesAndAddToModel(Model model, String attributeName) {
        List<PlaceDto> allPlacesDto = placeService.getAll();
        model.addAttribute(attributeName, allPlacesDto);
    }

    public void getAllPlacesByPartialMatchAndAddToModel(String text, Model model, String attributeName) {
        List<PlaceDto> allPlaces = placeService.getPlacesByPartialMatch(text);
        model.addAttribute(attributeName, allPlaces);
    }

    public void getPlaceByIdAndAddToModel(Long id, Model model, String attributeName) {
        PlaceDto place = placeService.getById(id);
        model.addAttribute(attributeName, place);
    }

    public void getAllCitiesAndAddToModel(Model model, String attributeName) {
        List<String> allCities = placeService.getAllCities();
        model.addAttribute(attributeName, allCities);
    }

}
