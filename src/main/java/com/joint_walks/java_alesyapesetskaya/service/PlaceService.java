package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface PlaceService {

    List<Place> getAll();
    List<String> getAllCities();
    List<Place> getPlacesByPartialMatch(String text);
    List<Place> getPlacesByCity(String city);
    Place getById(Long id);
    Place getPlaceByAddress(Address address);
    Place getPlaceByCityStreetHouseNumber(String city, String street, Integer houseNumber);
    void savePlace(Place place);
    void createAppointment(Address address, Date date, LocalTime time, String description);
}
