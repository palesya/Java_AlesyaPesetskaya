package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AddAppointmentForm;
import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.model.*;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import com.joint_walks.java_alesyapesetskaya.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
public abstract class AbstractAppointmentController extends AbstractPlaceController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PlaceService placeService;

    public void getAllAppointmentsAndAddToModel(Model model, String attributeName) {
        List<AppointmentDto> all = appointmentService.getAll();
        model.addAttribute(attributeName, all);
    }

    public void getAppointmentsWithoutUserAndAddToModel(Model model, String attributeName, UserSecurity userSecurity) {
        User userByLoginFromUserSecurity = getUserByLoginFromUserSecurity(userSecurity);
        Long userId = userByLoginFromUserSecurity.getId();
        List<AppointmentDto> all = appointmentService.getAppointmentsWithoutUser(userId);
        model.addAttribute(attributeName, all);
    }

    public void createAppointment(AddAppointmentForm appointmentForm, UserSecurity userSecurity) {
        Address address = appointmentForm.getAddress();
        Place placeByAddress = placeService.getPlaceByAddress(address);
        User userByLogin = getUserByLoginFromUserSecurity(userSecurity);
        appointmentService.createAppointment(new Appointment(placeByAddress,
                appointmentForm.getDate(), appointmentForm.getTime(), appointmentForm.getDescription()), userByLogin);

    }

    public void joinAppointmentAndAddSuccessMessageToModel(Model model, Long appointmentId, UserSecurity userSecurity) {
        User userByLogin = getUserByLoginFromUserSecurity(userSecurity);
        Appointment appointment = appointmentService.getById(appointmentId);
        Address address = appointment.getPlace().getAddress();
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(appointment.getDate());
        LocalTime time = appointment.getTime();
        appointmentService.joinAppointment(appointment, userByLogin);
        model.addAttribute("success", "You've been successfully added to the appointment. Date: " + date + ". Time: " + time + ". Address: " + address);
    }

    public void filterAppointmentsByCityAndAddToModel(String city, Model model, String appointmentsAttributeName) {
        List<AppointmentDto> allAppointments;
        if (Objects.equals(city, "All cities")) {
            allAppointments = appointmentService.getAll();
        } else {
            allAppointments = appointmentService.getAppointmentsByCity(city);
        }
        model.addAttribute(appointmentsAttributeName, allAppointments);
    }

    public void filterAppointmentsByCityWithoutUserAndAddToModel(String city, Model model, String appointmentsAttributeName, UserSecurity userSecurity) {
        Long userId = getUserByLoginFromUserSecurity(userSecurity).getId();
        List<AppointmentDto> allAppointments;
        if (Objects.equals(city, "All cities")) {
            allAppointments = appointmentService.getAppointmentsWithoutUser(userId);
        } else {
            List<AppointmentDto> allAppointmentsByCity = appointmentService.getAppointmentsByCity(city);
            allAppointments = appointmentService.excludeAppointmentsWithUser(userId, allAppointmentsByCity);
        }
        model.addAttribute(appointmentsAttributeName, allAppointments);
    }

    public void getAppointmentsByPartialMatchAndAddToModel(Model model, String text, String appointmentsAttributeName) {
        List<AppointmentDto> appointmentsByPartialMatch = appointmentService.getAppointmentsByPartialMatch(text);
        model.addAttribute(appointmentsAttributeName, appointmentsByPartialMatch);
    }

    public void getAppointmentsByPartialMatchWithoutUserAndAddToModel(Model model, String text, String appointmentsAttributeName, UserSecurity userSecurity) {
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByPartialMatch(text);
        Long userId = getUserByLoginFromUserSecurity(userSecurity).getId();
        List<AppointmentDto> appointmentsByPartialMatch = appointmentService.excludeAppointmentsWithUser(userId, appointments);
        model.addAttribute(appointmentsAttributeName, appointmentsByPartialMatch);
    }

    public void getUsersFromAppointmentAndAddToModel(Model model, Long appointmentId,String usersAttributeName) {
        List<User> users = appointmentService.getById(appointmentId).getUsers();
        model.addAttribute(usersAttributeName, users);
    }

    public void checkIfUserIsAddedToAppointmentAndAddToModel(UserSecurity userSecurity, Long appointmentId, Model model, String isUserAddedAttributeName) {
        Long userId = getUserByLoginFromUserSecurity(userSecurity).getId();
        List<Long> addedUserIds = appointmentService.getAddedUserIdsFromAppointment(appointmentId);
        boolean isUserAdded = addedUserIds.contains(userId);
        model.addAttribute("isUserAdded", isUserAdded);
    }

    public void getUserAppointmentsAndAddToModel(Long userId, Model model, String appointmentsAttributeName) {
        List<Appointment> appointmentsByUserId = appointmentService.getAppointmentsByUserId(userId);
        model.addAttribute(appointmentsAttributeName, appointmentsByUserId);
    }

    public void leaveAppointmentAndAddSuccessMessageToModel(Model model, Long appointmentId, Long userId, String successAttributeName) {
        Appointment appointment = appointmentService.getById(appointmentId);
        appointmentService.deleteUserFromOneAppointment(userId, appointmentId);
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(appointment.getDate());
        model.addAttribute(successAttributeName, "You've successfully left the appointment. Date: " + date + ". Time: " + appointment.getTime() + ". Address: " + appointment.getPlace().getAddress());
    }

    public void getAppointmentByIdAndAddToModel(Model model, Long appointmentId, String appointmentAttributeName) {
        Appointment appointment = appointmentService.getById(appointmentId);
        model.addAttribute(appointmentAttributeName, appointment);
    }

    public void getLoggedUserAndItsAppointmentsAndAddToModel(Model model,Long userId,String userAttributeName, String appointmentsAttributeName){
        getLoggedUserByIdAndAddToModel(userId,model,userAttributeName);
        getUserAppointmentsAndAddToModel(userId,model,appointmentsAttributeName);
    }
}
