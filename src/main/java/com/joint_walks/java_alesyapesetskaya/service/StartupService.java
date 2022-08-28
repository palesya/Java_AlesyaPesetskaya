package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class StartupService {

    @Autowired
    UserRepository repository;

    @PostConstruct
    public void init() {
        User user1 = new User("ALesya","pass1");
        User user2 = new User("Pavel","pass2");
        User user3 = new User("Sofiya","pass3");

        User[] users = {user1,user2,user3};
        for(User user:users){
            repository.save(user);
        }

        List<User> byIsDeletedIsFalse = repository.findByIsDeletedIsFalse();
        System.out.println(byIsDeletedIsFalse);
        System.out.println("aa");

    }

}
