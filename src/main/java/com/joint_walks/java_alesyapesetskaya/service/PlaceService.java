package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.Place;

import java.util.List;

public interface PlaceService {

    List<Place> getAll();

    List<String> getAllCities();
}
