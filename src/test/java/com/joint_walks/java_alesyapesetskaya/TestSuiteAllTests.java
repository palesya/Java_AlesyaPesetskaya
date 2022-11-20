package com.joint_walks.java_alesyapesetskaya;

import com.joint_walks.java_alesyapesetskaya.converter.StringToAddressConverterTest;
import com.joint_walks.java_alesyapesetskaya.repository.UserRepositoryTest;
import com.joint_walks.java_alesyapesetskaya.service.AppointmentServiceImplTest;
import com.joint_walks.java_alesyapesetskaya.service.UserServiceImplTest;
import com.joint_walks.java_alesyapesetskaya.validator.ImageFileValidatorTest;
import com.joint_walks.java_alesyapesetskaya.validator.LoginForRegisterValidatorTest;
import com.joint_walks.java_alesyapesetskaya.validator.TimeValidatorTest;
import com.joint_walks.java_alesyapesetskaya.web.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        StringToAddressConverterTest.class,
        UserRepositoryTest.class,
        AppointmentServiceImplTest.class,
        UserServiceImplTest.class,
        ImageFileValidatorTest.class,
        LoginForRegisterValidatorTest.class,
        TimeValidatorTest.class,
        DogsControllerTest.class,
        LoginControllerTest.class,
        MainControllerTest.class,
        PersonalPageControllerTest.class,
        RegisterPageControllerTest.class,
        SmokeTest.class,
        WalkPlacesControllerTest.class,
        AppointmentInfoControllerTest.class,
        JoinControllerTest.class,
        AddAppointmentControllerTest.class
})
public class TestSuiteAllTests {

}
