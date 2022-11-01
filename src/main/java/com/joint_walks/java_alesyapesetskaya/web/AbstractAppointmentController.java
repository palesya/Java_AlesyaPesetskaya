package com.joint_walks.java_alesyapesetskaya.web;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public abstract class AbstractAppointmentController extends AbstractPlaceController{

    @Autowired
    private AppointmentService appointmentService;

    public void getAllAppointmentsAndAddToModel(Model model, String attributeName) {
        List<AppointmentDto> all = appointmentService.getAll();
        model.addAttribute(attributeName, all);
    }

}
