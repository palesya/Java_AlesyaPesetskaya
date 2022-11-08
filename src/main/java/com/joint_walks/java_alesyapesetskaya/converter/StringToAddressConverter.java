package com.joint_walks.java_alesyapesetskaya.converter;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAddressConverter implements Converter<String, Address> {

    @Autowired
    private PlaceServiceImpl placeService;

    @Override
    public Address convert(String source) {
        if (source.isBlank()) {
            return null;
        } else {
            String[] s = source.split(" ");
            String city = s[0].substring(0, s[0].length() - 1);
            String street = s[1];
            Integer houseNumber = Integer.parseInt(s[3]);
            System.out.println(city + street + houseNumber);
            Place place = placeService.getPlaceByCityStreetHouseNumber(city, street, houseNumber);
            return place.getAddress();
        }
    }
}
