package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.model.UserSecurity;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/dogwalker")
public class DogsController extends AbstractUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/user/dogs")
    public String getForUser(Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        getNotDeletedUsersAndAddToModel(model, "users");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "dogs";
    }

    @PostMapping("/user/dogs")
    public String searchDogForUser(
            @RequestParam(name = "search_text") String text,
            Model model,
            @AuthenticationPrincipal UserSecurity userSecurity) {
        getUsersByPartialMatchAndAddToModel(text, model, "users");
        getLoggedUserByUserSecurityLoginAndAddToModel(userSecurity, model, "loggedUser");
        return "dogs";
    }

    @GetMapping("/admin/dogs")
    public String getForAdmin(Model model) {
        getNotDeletedUsersAndAddToModel(model, "users");
        return "dogsAdmin";
    }

    @PostMapping("/admin/dogs")
    public String searchDogForAdmin(
            @RequestParam(name = "search_text") String text,
            Model model) {
        getUsersByPartialMatchAndAddToModel(text, model, "users");
        return "dogsAdmin";
    }

    @PostMapping("/admin/dogs/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             Model model) {
        userService.setIsDeletedToTrue(id);
        appointmentService.deleteUserFromAppointments(id);
        getNotDeletedUsersAndAddToModel(model, "users");
        return "dogsAdmin";
    }

}
