package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
