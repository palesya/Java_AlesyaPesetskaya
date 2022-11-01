package com.joint_walks.java_alesyapesetskaya.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;
    private String transportStop;
    private boolean trainingEqyipment;
    private boolean fence;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String base64Image;

    public Place(Address address, String transportStop, boolean trainingEqyipment, boolean fence,String base64Image) {
        this.address = address;
        this.transportStop = transportStop;
        this.trainingEqyipment = trainingEqyipment;
        this.fence = fence;
        this.base64Image = base64Image;
    }
}
