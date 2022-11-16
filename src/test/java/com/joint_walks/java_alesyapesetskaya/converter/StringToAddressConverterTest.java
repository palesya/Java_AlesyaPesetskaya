package com.joint_walks.java_alesyapesetskaya.converter;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Place;
import com.joint_walks.java_alesyapesetskaya.repository.PlaceRepository;
import com.joint_walks.java_alesyapesetskaya.service.PlaceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringToAddressConverterTest {

    @InjectMocks
    private PlaceServiceImpl placeService;
    @Mock
    private PlaceRepository repository;
    @Mock
    private PlaceMapperUtils mapper;

    @Test
    public void testConverterWithValidSource(){
        StringToAddressConverter converter = new StringToAddressConverter(placeService);
        String source = "Minsk, Nebesnaya str. 12";
        String city = "Minsk";
        String street = "Nebesnaya";
        Integer houseNumber = 12;
        Address address = Address.builder()
                .street(street)
                .city(city)
                .houseNumberNearby(houseNumber)
                .build();
        Place place = Place.builder()
                .address(address)
                .build();
        Mockito.when(placeService.getPlaceByCityStreetHouseNumber(city,street,houseNumber)).thenReturn(place);
        Address converted = converter.convert(source);
        Assertions.assertEquals(converted,address);
    }

    @Test
    public void testConverterWhenBlankSource(){
        StringToAddressConverter converter = new StringToAddressConverter(placeService);
        String blankSource = "";
        Address converted = converter.convert(blankSource);
        Assertions.assertNull(converted);
    }

}