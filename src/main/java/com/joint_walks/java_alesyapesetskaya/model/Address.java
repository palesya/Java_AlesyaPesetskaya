package com.joint_walks.java_alesyapesetskaya.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private Integer houseNumberNearby;
    @OneToOne(mappedBy = "address")
    private Place place;

    public Address(String city, String street, Integer houseNumberNearby) {
        this.city = city;
        this.street = street;
        this.houseNumberNearby = houseNumberNearby;
    }

    @Override
    public String toString() {
        return city + ", "+street+" str. "+ houseNumberNearby;
    }
}
