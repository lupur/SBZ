package com.sbz.agro.service;

import com.sbz.agro.dto.UserRegistrationDto;
import com.sbz.agro.model.User;

public interface UserService {

    User findByUsername(String username);

    boolean registerNewUser(UserRegistrationDto userDto);

    String login(String username, String password);

    boolean isUserLoggedIn(String token);

    void logout(String token);
}
