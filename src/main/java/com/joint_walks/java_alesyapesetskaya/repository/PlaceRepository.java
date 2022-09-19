package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Transactional
    @Query("select p from Place p JOIN p.address a " +
            "where lower(p.transportStop) LIKE lower(CONCAT('%',:text,'%')) " +
            "OR LOWER(a.street) LIKE lower(CONCAT('%',:text,'%'))")
    List<Place> getPlacesByPartialMatch(String text);

    @Transactional
    @Query("select p from Place p JOIN p.address a " +
            "where LOWER(a.city) LIKE lower(CONCAT('%',:city,'%'))")
    List<Place> getPlacesByCity(String city);

}
