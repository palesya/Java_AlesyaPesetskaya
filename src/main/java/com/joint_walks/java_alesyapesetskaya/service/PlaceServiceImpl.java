package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.PlaceMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
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
    private PlaceRepository repository;
    @Autowired
    private PlaceMapperUtils mapper;

    @Override
    public List<PlaceDto> getAll() {
        List<Place> allPlacesFromDB = repository.findAll();
        return mapper.mapToListPlaceDTO(allPlacesFromDB);
    }

    @Override
    public List<String> getAllCities() {
        List<PlaceDto> allPlaces = getAll();
        List<String> cities = new ArrayList<>();
        for (PlaceDto place : allPlaces) {
            String city = place.getAddress().getCity();
            cities.add(city);
        }
        return cities.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<PlaceDto> getPlacesByPartialMatch(String text) {
        List<Place> placesFromDb = repository.getPlacesByPartialMatch(text);
        return mapper.mapToListPlaceDTO(placesFromDb);
    }

    @Override
    public List<PlaceDto> getPlacesByCity(String city) {
        List<Place> placesByCityFromDb = repository.getPlacesByCity(city);
        return mapper.mapToListPlaceDTO(placesByCityFromDb);
    }

    @Override
    public PlaceDto getById(Long id) {
        Place placeFromDb = repository.getById(id);
        return mapper.mapToPlaceDTO(placeFromDb);
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
