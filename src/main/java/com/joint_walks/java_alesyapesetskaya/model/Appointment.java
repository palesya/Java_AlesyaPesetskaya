package com.joint_walks.java_alesyapesetskaya.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id"),
            name = "place_appointment")
    private Place place;
    private Date date;
    private LocalTime time;
    private String description;
    private Integer numberOfPeople;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "appointment_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"),
    name = "user_appointment")
    private List<User> users;


    public Appointment(Place place, Date date, LocalTime time, String description) {
        this.place = place;
        this.date = date;
        this.time = time;
        this.description = description;
    }

}
