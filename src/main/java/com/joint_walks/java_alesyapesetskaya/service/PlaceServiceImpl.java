package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository repository;

    @Override
    public List<Place> getAll() {
        return repository.findAll();
    }

    @Override
    public List<String> getAllCities() {
        List<Place> allPlaces = getAll();
        List<String> cities = new ArrayList<>();
        for (Place place : allPlaces) {
            String city = place.getAddress().getCity();
            cities.add(city);
        }
        return cities.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Place> getPlacesByPartialMatch(String text) {
        return repository.getPlacesByPartialMatch(text);
    }

    @Override
    public List<Place> getPlacesByCity(String city) {
        return repository.getPlacesByCity(city);
    }

    @Override
    public Place getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Place getPlaceByAddress(Address address) {
        Place place = repository.getPlaceByCityStreetHouseNumber(address.getCity(), address.getStreet(), address.getHouseNumberNearby());
        return place;
    }

    @Override
    public Place getPlaceByCityStreetHouseNumber(String city, String street, Integer houseNumber) {
        return repository.getPlaceByCityStreetHouseNumber(city, street, houseNumber);
    }

    @Override
    public void savePlace(Place place) {
        repository.saveAndFlush(place);
    }

    @Override
    public void createAppointment(Address address, Date date, LocalTime time, String description) {
        Place place = getPlaceByAddress(address);
        Appointment appointment = new Appointment(place,date,time,description);
        appointment.setNumberOfPeople(1);
        List<Appointment> appointments = place.getAppointments();
        appointments.add(appointment);
        place.setAppointments(appointments);
        savePlace(place);
    }

}
