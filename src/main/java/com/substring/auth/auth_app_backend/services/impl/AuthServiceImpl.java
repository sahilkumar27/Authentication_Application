package com.substring.auth.auth_app_backend.services.impl;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.services.AuthService;
import com.substring.auth.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Override
    public UserDto registerUser(UserDto userDto) {
        //logic
        //verify email
        //verify password
        //default roles
        UserDto userDto1 = userService.createUser(userDto);


        return userDto1;
    }
}

//@Service
//@AllArgsConstructor - thia are the lambok function, it will create constructor for all the final fields in the class (download if you don't have lombok plugin in your ide),
// if you don't want to use lombok, you can create constructor manually, as getter and setter methods for all the fields in the class.