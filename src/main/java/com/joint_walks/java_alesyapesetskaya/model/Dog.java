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
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Integer age;
    @Lob
    private String base64Image;
    private SEX sex;

    public Dog(String name, String type, Integer age, String base64Image,SEX sex) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.base64Image = base64Image;
        this.sex=sex;
    }
}
