package com.joint_walks.java_alesyapesetskaya.converter;

import com.joint_walks.java_alesyapesetskaya.dto.AppointmentDto;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentMapperUtils {

    public AppointmentDto mapToAppointmentDTO(Appointment appointmentFromDB) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(appointmentFromDB, AppointmentDto.class);
    }

    public List<AppointmentDto> mapToListAppointmentDTO(List<Appointment> appointmentsFromDB) {
        List<AppointmentDto> allAppointmentsDTO = new ArrayList<>();
        for (Appointment appointment : appointmentsFromDB) {
            AppointmentDto appointmentDto = mapToAppointmentDTO(appointment);
            allAppointmentsDTO.add(appointmentDto);
        }
        return allAppointmentsDTO;
    }

}
