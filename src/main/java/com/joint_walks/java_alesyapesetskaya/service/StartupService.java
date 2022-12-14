package com.joint_walks.java_alesyapesetskaya.service;

import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.repository.PlaceRepository;
import com.joint_walks.java_alesyapesetskaya.repository.RoleRepository;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StartupService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() throws IOException {

        String pathPart = "./src/main/resources/static/";

        String avatar1 = getStringBase64FromFile(pathPart + "user1.jpg");
        String avatar2 = getStringBase64FromFile(pathPart + "user2.jpg");
        String avatar3 = getStringBase64FromFile(pathPart + "user3.jpg");
        String avatar4 = getStringBase64FromFile(pathPart + "user4.jpg");

        String dogPhoto1 = getStringBase64FromFile(pathPart + "dog1.jpg");
        String dogPhoto2 = getStringBase64FromFile(pathPart + "dog2.jpg");
        String dogPhoto3 = getStringBase64FromFile(pathPart + "dog3.jpg");
        String dogPhoto4 = getStringBase64FromFile(pathPart + "dog4.jpg");

        Dog dog1 = new Dog("Lucky", "French bulldog", 1, dogPhoto1, SEX.MAN);
        Dog dog2 = new Dog("Lucy", "English setter", 4, dogPhoto2, SEX.WOMAN);
        Dog dog3 = new Dog("Crunchy", "Mops", 10, dogPhoto3, SEX.MAN);
        Dog dog4 = new Dog("Snoopy", "Taksa", 2, dogPhoto4, SEX.MAN);

        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        List<Role> roles = List.of(userRole, adminRole);
        List<String> allRoleNamesInDb = roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
        for (Role role : roles) {
            if (!allRoleNamesInDb.contains(role.getName())) {
                roleRepository.save(role);
            }
        }

        User admin = new User("admin","$2a$12$Gh4ZzQM1UL/WAw/rdpvFIeRsBegJ7IaHDEAUIoVmumB39z4wMBBcm");

        User user1 = new User("Alesya", "$2a$12$JGRFmRlvwqm5IdcmmZlVVuJgAiBw/ZBJ4OgsHVE/Iv4Wzv37W0DMK", 32, avatar1, dog1);
        User user2 = new User("Pavel", "$2a$12$fEePR7vnSE.Xmd0XFt7LBe0TiUsnkh0T7hrpKW42reK3JVGC.5dRS", 35, avatar2, dog2);
        User user3 = new User("Sofiya", "$2a$12$2XlLYPd1v/AYjr/Yts4VCezVOPsphAAWM3NuCk91bnoUDUoFNmV32", 24, avatar3, dog3);
        User user4 = new User("Snoop Dogg", "$2a$12$TXclXk9J5uJk89HpCWR9fedFF15pYK/YFflgfA2ZlXUQdk8t54jYW", 24, avatar4, dog4);
        List<User> users = List.of(user1, user2, user3,user4);
        List<String> allLogins = userRepository.findAll().stream().map(User::getLogin).collect(Collectors.toList());
        user4.setDeleted(true);
        for (User user : users) {
            if (!allLogins.contains(user.getLogin())) {
                user.addRole(userRole);
                userRepository.saveAndFlush(user);
            }
        }
        if (!allLogins.contains(admin.getLogin())) {
            admin.addRole(adminRole);
            userRepository.saveAndFlush(admin);
        }


        Address address1 = new Address("Minsk", "Main", 12);
        Address address2 = new Address("Grodno", "Nebesnaya", 4);
        Address address3 = new Address("Brest", "Svetlaya", 48);
        Address address4 = new Address("Minsk", "Lake", 3);

        String stringFromImage1 =
                getStringBase64FromFile(pathPart + "place1.jpg");

        String stringFromImage2 =
                getStringBase64FromFile(pathPart + "place2.jpg");

        String stringFromImage3 =
                getStringBase64FromFile(pathPart + "place3.jpg");

        String stringFromImage4 =
                getStringBase64FromFile(pathPart + "place4.jpg");

        Place place1 = new Place(address1, "Angarskaya 3", true, true, stringFromImage1);
        Place place2 = new Place(address2, "Borovaya", true, true, stringFromImage2);
        Place place3 = new Place(address3, "School 3", true, false, stringFromImage3);
        Place place4 = new Place(address4, "Big lake", false, false, stringFromImage4);

        List<Place> places = List.of(place1, place2, place3, place4);
        List<Address> allAddresses = placeRepository.findAll().stream().map(Place::getAddress).collect(Collectors.toList());
        for (Place place : places) {
            if (!allAddresses.contains(place.getAddress())) {
                placeRepository.saveAndFlush(place);
            }
        }
    }

    private String getStringBase64FromFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

}

