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
    @Builder.Default
    private boolean isDeleted = false;

    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
    @Version
    private long version;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
