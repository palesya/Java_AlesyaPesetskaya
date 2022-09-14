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
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;
    private String transportStop;
    private boolean trainingEqyipment;
    private boolean fence;
    @Lob
    private String base64Image;

    public Place(Address address, String transportStop, boolean trainingEqyipment, boolean fence,String base64Image) {
        this.address = address;
        this.transportStop = transportStop;
        this.trainingEqyipment = trainingEqyipment;
        this.fence = fence;
        this.base64Image = base64Image;
    }
}
