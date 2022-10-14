package com.joint_walks.java_alesyapesetskaya.converter;

import com.joint_walks.java_alesyapesetskaya.dto.UserDto;
import com.joint_walks.java_alesyapesetskaya.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapperUtils {

    public UserDto mapToUserDTO(User userFromDB) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userFromDB, UserDto.class);
    }

    public List<UserDto> mapToListUserDTO(List<User> usersFromDB) {
        List<UserDto> allUsersDTO = new ArrayList<>();
        for (User user : usersFromDB) {
            UserDto userDto = mapToUserDTO(user);
            allUsersDTO.add(userDto);
        }
        return allUsersDTO;
    }

}
