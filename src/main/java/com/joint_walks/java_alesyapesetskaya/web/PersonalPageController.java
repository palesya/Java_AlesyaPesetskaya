package com.joint_walks.java_alesyapesetskaya.web;


import com.joint_walks.java_alesyapesetskaya.model.Dog;
import com.joint_walks.java_alesyapesetskaya.model.User;
import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/dogwalker/personalPage")
public class PersonalPageController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        User userById = userService.getById(id);
        model.addAttribute("loggedUser", userById);
        return "personalPage";
    }

    @PostMapping("/{id}/changeData")
    public String getChanges(@PathVariable Long id, Model model) {
        User userById = userService.getById(id);
        model.addAttribute("loggedUser", userById);
        return "changeData";
    }

    @PostMapping("/{id}/savedChanges")
    public String saveChanges(@PathVariable Long id,
                              Model model,
                              @RequestParam(name = "ownerAge",required = false) Integer ownerAge,
                              @RequestParam(name = "dogName",required = false) String dogName,
                              @RequestParam(name = "dogType",required = false) String dogType,
                              @RequestParam(name = "dogAge",required = false) Integer dogAge) {
        User userById = userService.getById(id);
        model.addAttribute("loggedUser", userById);
        if (ownerAge!=null && ownerAge > 0) {
            userById.setAge(ownerAge);
        }
        if (dogName!=null && !dogName.isBlank()) {
            Dog dog = userById.getDog();
            dog.setName(dogName);
        }
        if (dogType!=null && !dogType.isBlank()) {
            Dog dog = userById.getDog();
            dog.setType(dogType);
        }
        if (dogAge!=null && dogAge >= 0) {
            Dog dog = userById.getDog();
            dog.setAge(dogAge);
        }
        userById.setId(id);
        userService.saveUser(userById);
        return "personalPage";
    }


}
