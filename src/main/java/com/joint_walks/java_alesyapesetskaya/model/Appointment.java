package com.joint_walks.java_alesyapesetskaya.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Place place;
    private Date date;
    private LocalTime time;
    private String description;
    private Integer numberOfPeople;

    public Appointment(Place place, Date date, LocalTime time, String description) {
        this.place = place;
        this.date = date;
        this.time = time;
        this.description = description;
    }
}
