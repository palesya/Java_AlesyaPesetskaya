package com.joint_walks.java_alesyapesetskaya.repository;

import com.joint_walks.java_alesyapesetskaya.model.Address;
import com.joint_walks.java_alesyapesetskaya.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Transactional
    @Query("select a from Appointment a where a.id=:id")
    Appointment getById(Long id);

    @Transactional
    @Query("select a from Appointment a JOIN a.place p JOIN p.address adr " +
            "where LOWER(adr.city) LIKE lower(CONCAT('%',:city,'%'))")
    List<Appointment> getAppointmentsByCity(String city);

    @Transactional
    @Query("select a from Appointment a JOIN a.place p JOIN p.address adr " +
            "where lower(p.transportStop) LIKE lower(CONCAT('%',:text,'%')) " +
            "OR LOWER(adr.street) LIKE lower(CONCAT('%',:text,'%'))")
    List<Appointment> getAppointmentsByPartialMatch(String text);

    @Transactional
    @Query(value = "select user_id from user_appointment where appointment_id=?",nativeQuery = true)
    List<Long> getAddedUserIdsFromAppointment(Long appointmentId);

    @Transactional
    @Query(value = "select count(*) from user_appointment ua join users u on ua.user_id=u.id where ua.appointment_id=? and u.is_deleted=false",nativeQuery = true)
    Integer getNumberOfAddedUsers(Long appointmentId);

    @Transactional
    @Query("select a from Appointment a JOIN a.users u where u.id=:userId")
    List<Appointment> getAppointmentsByUserId(Long userId);

    @Transactional
    @Query("select a from Appointment a JOIN a.place p where a.date=:date and p.address=:address")
    List<Appointment> findByDateAndAddress(Date date, Address address);

    @Transactional
    @Query(value = "select a.id from appointments a except select a.id from appointments a JOIN user_appointment ua on a.id = ua.appointment_id where ua.user_id=?",nativeQuery = true)
    List<Long> getAppointmentsIdsWithoutUserId(Long userId);

}
