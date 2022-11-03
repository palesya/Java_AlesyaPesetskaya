package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.converter.UserMapperUtils;
import com.joint_walks.java_alesyapesetskaya.dto.RegisterForm;
import com.joint_walks.java_alesyapesetskaya.model.Dog;
import com.joint_walks.java_alesyapesetskaya.model.Role;
import com.joint_walks.java_alesyapesetskaya.model.SEX;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.joint_walks.java_alesyapesetskaya.model.SEX.MAN;
import static com.joint_walks.java_alesyapesetskaya.model.SEX.WOMAN;

@Controller
@RequestMapping(path = "/register")
public class RegisterPageController extends AbstractUserController {

    @Autowired
    private UserService userService;

    private final String rootDir = System.getProperty("user.dir") + "/src/main/webapp/static/images";

    @GetMapping
    public String get(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping
    public String saveChanges(@Valid @ModelAttribute(name = "registerForm") RegisterForm registerForm,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        String dogImageString = getStringBase64FromFile(registerForm.getDogImage());
        SEX dogSex = (Objects.equals(registerForm.getDogSexString(), "girl")) ? WOMAN : MAN;
        Dog dog = new Dog(registerForm.getDogName(), registerForm.getDogType(), registerForm.getDogAge(), dogImageString, dogSex);
        String encodedPassword = new BCryptPasswordEncoder().encode(registerForm.getPassword());
        String userImageString = getStringBase64FromFile(registerForm.getUserImage());
        Role userRole = new Role("USER");
        List<Role> rolesForSimpleUsers = new ArrayList<>();
        rolesForSimpleUsers.add(userRole);
        User user = new User(registerForm.getLogin(), encodedPassword, registerForm.getUserAge(), userImageString, dog);
        user.setRoles(rolesForSimpleUsers);
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
