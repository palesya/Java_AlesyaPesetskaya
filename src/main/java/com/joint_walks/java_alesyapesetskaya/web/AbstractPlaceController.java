package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;

@Controller
public abstract class AbstractPlaceController extends AbstractUserController {

    @Autowired
    private PlaceService placeService;

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

    public void addAllPlacesAndLoggedUserToModel(Model model, String placesAttributeName, UserSecurity userSecurity, String userAttributeName) {
        getAllPlacesAndAddToModel(model, placesAttributeName);
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, userAttributeName);
    }

    public void showAddressIfItWasSelected(AddAppointmentForm appointmentForm, Model model, String selectedPlaceAttributeName) {
        if (appointmentForm.getAddress() != null) {
            Long id = appointmentForm.getAddress().getId();
            getPlaceByIdAndAddToModel(id, model, selectedPlaceAttributeName);
        }
    }

    public void filterPlacesByCityAndAddToModel(String city, Model model, String appointmentsAttributeName) {
        List<PlaceDto> allPlaces;
        if (Objects.equals(city, "All cities")) {
            allPlaces = placeService.getAll();
        } else {
            allPlaces = placeService.getPlacesByCity(city);
        }
        model.addAttribute(appointmentsAttributeName, allPlaces);
    }

    public void getAllCitiesAndLoggedUserAddToModel(Model model, String allCitiesAttributeName,UserSecurity userSecurity, String userAttributeName){
        getAllCitiesAndAddToModel(model,allCitiesAttributeName);
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity,model,userAttributeName);
    }

}
