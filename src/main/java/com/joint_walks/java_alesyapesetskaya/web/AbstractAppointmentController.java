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

    public void createAppointment(AddAppointmentForm appointmentForm, UserSecurity userSecurity) {
        Address address = appointmentForm.getAddress();
        Place placeByAddress = placeService.getPlaceByAddress(address);
        User userByLogin = getUserByLoginFromUserSecurity(userSecurity);
        appointmentService.createAppointment(new Appointment(placeByAddress,
                appointmentForm.getDate(), appointmentForm.getTime(), appointmentForm.getDescription()), userByLogin);

    }

    public void joinAppointmentAndAddItToModel(Model model, Long appointmentId, UserSecurity userSecurity) {
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
            allAppointments = appointmentService.getAppointmentByCity(city);
        }
        model.addAttribute(appointmentsAttributeName, allAppointments);
    }

    public void getAppointmentsByPartialMatchAndAddToModel(Model model, String text, String appointmentsAttributeName) {
        List<AppointmentDto> appointmentsByPartialMatch = appointmentService.getAppointmentsByPartialMatch(text);
        model.addAttribute(appointmentsAttributeName, appointmentsByPartialMatch);
    }
}
