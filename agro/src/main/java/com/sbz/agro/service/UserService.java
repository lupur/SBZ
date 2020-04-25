package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.GetUserDto;
import com.sbz.agro.dto.UserLoginDto;
import com.sbz.agro.dto.UserRegistrationDto;
import com.sbz.agro.model.User;

public interface UserService {

    User findByUsername(String username);

    boolean registerNewUser(UserRegistrationDto userDto);

    String login(UserLoginDto user);

    boolean isUserLoggedIn(String token);

    void logout(String token);

    List<GetUserDto> getAllUsers();
}
