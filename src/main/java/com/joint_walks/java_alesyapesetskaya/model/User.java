package com.joint_walks.java_alesyapesetskaya.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private Integer age;
    @Builder.Default
    private boolean isDeleted = false;
    @Lob
    private String base64Image;
    @OneToOne(cascade = CascadeType.ALL)
    private Dog dog;

    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
    @Version
    private long version;

    public User(String login, String password, Integer age,String base64Image,Dog dog) {
        this.login = login;
        this.password = password;
        this.age=age;
        this.base64Image=base64Image;
        this.dog=dog;
    }
}
