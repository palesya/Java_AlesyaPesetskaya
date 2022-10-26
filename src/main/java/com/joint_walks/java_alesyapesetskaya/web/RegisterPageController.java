package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.model.Dog;
import com.joint_walks.java_alesyapesetskaya.model.SEX;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.joint_walks.java_alesyapesetskaya.model.SEX.MAN;
import static com.joint_walks.java_alesyapesetskaya.model.SEX.WOMAN;

@Controller
@RequestMapping(path = "/register")
public class RegisterPageController extends AbstractUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapperUtils converter;

    private final String rootDir = System.getProperty("user.dir")+"/src/main/webapp/static/images";

    @GetMapping
    public String get(Model model) {

        return "register";
    }

    @PostMapping
    public String saveChanges(Model model,
                              @RequestParam(name = "login", required = false) String login,
                              @RequestParam(name = "user_age", required = false) Integer userAge,
                              @RequestParam(name = "password", required = false) String password,
                              @RequestParam(name = "repeat_password", required = false) String repeatPassword,
                              @RequestParam(name = "user_image", required = false) MultipartFile userImage,
                              @RequestParam(name = "dog_name", required = false) String dogName,
                              @RequestParam(name = "dog_type", required = false) String dogType,
                              @RequestParam(name = "sex", required = false) String dogSexString,
                              @RequestParam(name = "dog_age", required = false) Integer dogAge,
                              @RequestParam(name = "dog_image", required = false) MultipartFile dogImage) throws IOException {
        System.out.println("-----------------------------------");
        String dogImageString = getStringBase64FromFile(dogImage);
        SEX dogSex = (Objects.equals(dogSexString, "girl")) ? WOMAN : MAN;
        Dog dog = new Dog(dogName,dogType,dogAge,dogImageString,dogSex);
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        String userImageString = getStringBase64FromFile(userImage);
        User user = new User(login, encodedPassword, userAge,userImageString,dog);
        userService.saveUser(user);
        return "success_register";
    }

    private String getStringBase64FromFile(MultipartFile file) throws IOException {
        Path path = Paths.get(rootDir, file.getName());
        Files.write(path, file.getBytes());
        File file1 = new File(String.valueOf(path));
        FileInputStream fileInputStreamReader = new FileInputStream(file1);
        byte[] bytes = new byte[(int) file1.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

}
