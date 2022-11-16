package com.joint_walks.java_alesyapesetskaya.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public User(String login, String password, Integer age, String base64Image, Dog dog) {
        this.login = login;
        this.password = password;
        this.age = age;
        this.base64Image = base64Image;
        this.dog = dog;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
