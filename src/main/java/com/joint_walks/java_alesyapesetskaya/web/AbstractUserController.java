package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.RegisterForm;
import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.service.RoleService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.joint_walks.java_alesyapesetskaya.model.SEX.MAN;
import static com.joint_walks.java_alesyapesetskaya.model.SEX.WOMAN;

@Controller
public abstract class AbstractUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private final String rootDir = System.getProperty("user.dir") + "/src/main/webapp/static/images";

    public void getLoggedUserByIdAndAddToModel(Long id, Model model, String attributeName) {
        UserDto userDto = userService.getUserDtoById(id);
        model.addAttribute(attributeName, userDto);
    }

    public void getUsersByPartialMatchAndAddToModel(String text, Model model, String attributeName) {
        List<UserDto> usersDto = userService.getUsersByPartialMatch(text);
        model.addAttribute(attributeName, usersDto);
    }

    public void getNotDeletedUsersAndAddToModel(Model model, String attributeName) {
        List<UserDto> allUsers = userService.getAllNotDeleted();
        model.addAttribute(attributeName, allUsers);
    }

    public void getLoggedUserByUserSecurityLoginAndAddToModel(UserSecurity userSecurity,
                                                              Model model,
                                                              String attributeName) {
        String securityUserLogin = userSecurity.getUsername();
        UserDto userDto = userService.getUserDtoByLogin(securityUserLogin);
        model.addAttribute(attributeName, userDto);
    }

    public User getUserByLoginFromUserSecurity(UserSecurity userSecurity) {
        String securityUserLogin = userSecurity.getUsername();
        return userService.getUserByLogin(securityUserLogin);
    }

    public void saveNewUser(RegisterForm registerForm) throws IOException {
        String dogImageString = getStringBase64FromFile(registerForm.getDogImage());
        SEX dogSex = (Objects.equals(registerForm.getDogSexString(), "girl")) ? WOMAN : MAN;
        Dog dog = new Dog(registerForm.getDogName(), registerForm.getDogType(), registerForm.getDogAge(), dogImageString, dogSex);
        String encodedPassword = new BCryptPasswordEncoder().encode(registerForm.getPassword());
        String userImageString = getStringBase64FromFile(registerForm.getUserImage());
        User user = new User(registerForm.getLogin(), encodedPassword, registerForm.getUserAge(), userImageString, dog);
        Role userRole = new Role("ROLE_USER");
        Role roleByNameFromDB = roleService.getRoleByName(userRole.getName());
        user.addRole(roleByNameFromDB);
        userService.saveUser(user);
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
