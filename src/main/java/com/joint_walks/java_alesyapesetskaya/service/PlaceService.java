package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;

import java.util.List;

public interface PlaceService {

    List<PlaceDto> getAll();
    List<String> getAllCities();
    List<PlaceDto> getPlacesByPartialMatch(String text);
    List<PlaceDto> getPlacesByCity(String city);
    PlaceDto getById(Long id);
    Place getPlaceByAddress(Address address);
    Place getPlaceByCityStreetHouseNumber(String city, String street, Integer houseNumber);
    void savePlace(Place place);
}
