package com.joint_walks.java_alesyapesetskaya.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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
    @OneToOne(mappedBy = "address",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(houseNumberNearby, address.houseNumberNearby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, houseNumberNearby);
    }
}
