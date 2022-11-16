package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.converter.PlaceMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.repository.AppointmentRepository;
import com.joint_walks.java_alesyapesetskaya.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository repository;
    private final PlaceMapperUtils mapper;

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
        return repository.getPlaceByCityStreetHouseNumber(address.getCity(), address.getStreet(), address.getHouseNumberNearby());
    }

    @Override
    public Place getPlaceByCityStreetHouseNumber(String city, String street, Integer houseNumber) {
        return repository.getPlaceByCityStreetHouseNumber(city, street, houseNumber);
    }

    @Override
    public void savePlace(Place place) {
        repository.saveAndFlush(place);
    }

}
