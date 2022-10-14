package com.joint_walks.java_alesyapesetskaya.converter;

import com.joint_walks.java_alesyapesetskaya.dto.PlaceDto;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceMapperUtils {

    public PlaceDto mapToPlaceDTO(Place placeFromDb) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(placeFromDb, PlaceDto.class);
    }

    public List<PlaceDto> mapToListPlaceDTO(List<Place> placesFromDB) {
       List<PlaceDto> allPlacesDto = new ArrayList<>();
       for(Place place:placesFromDB){
           PlaceDto placeDto = mapToPlaceDTO(place);
           allPlacesDto.add(placeDto);

       }
        return allPlacesDto;
    }

}
