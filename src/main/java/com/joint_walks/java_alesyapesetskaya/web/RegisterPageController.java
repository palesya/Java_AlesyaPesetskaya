package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.RegisterForm;
import com.joint_walks.java_alesyapesetskaya.model.Dog;
import com.joint_walks.java_alesyapesetskaya.model.SEX;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
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

    private final String rootDir = System.getProperty("user.dir") + "/src/main/webapp/static/images";

    @GetMapping
    public String get(Model model) {
        System.out.println("hello");
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

//    @PostMapping
//    public String saveChanges(Model model,
//                              @RequestParam(name = "login") String login,
//                              @RequestParam(name = "user_age") Integer userAge,
//                              @RequestParam(name = "password") String password,
//                              @RequestParam(name = "repeat_password") String repeatPassword,
//                              @RequestParam(name = "user_image") MultipartFile userImage,
//                              @RequestParam(name = "dog_name") String dogName,
//                              @RequestParam(name = "dog_type") String dogType,
//                              @RequestParam(name = "sex") String dogSexString,
//                              @RequestParam(name = "dog_age") Integer dogAge,
//                              @RequestParam(name = "dog_image") MultipartFile dogImage) throws IOException {
//        System.out.println("-----------------------------------");
//        String dogImageString = getStringBase64FromFile(dogImage);
//        SEX dogSex = (Objects.equals(dogSexString, "girl")) ? WOMAN : MAN;
//        Dog dog = new Dog(dogName,dogType,dogAge,dogImageString,dogSex);
//        String encodedPassword = new BCryptPasswordEncoder().encode(password);
//        String userImageString = getStringBase64FromFile(userImage);
//        User user = new User(login, encodedPassword, userAge,userImageString,dog);
//        userService.saveUser(user);
//        return "success_register";
//    }

    @PostMapping
    public String saveChanges(@Valid @ModelAttribute(name = "registerForm") RegisterForm registerForm,
                              BindingResult bindingResult,
                              Model model) throws IOException {
        System.out.println("hello");
        if (bindingResult.hasErrors()) {
            return "register";
        }
            String dogImageString = getStringBase64FromFile(registerForm.getDogImage());
            SEX dogSex = (Objects.equals(registerForm.getDogSexString(), "girl")) ? WOMAN : MAN;
            Dog dog = new Dog(registerForm.getDogName(), registerForm.getDogType(), registerForm.getDogAge(), dogImageString, dogSex);
            String encodedPassword = new BCryptPasswordEncoder().encode(registerForm.getPassword());
            String userImageString = getStringBase64FromFile(registerForm.getUserImage());
            User user = new User(registerForm.getLogin(), encodedPassword, registerForm.getUserAge(), userImageString, dog);
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
