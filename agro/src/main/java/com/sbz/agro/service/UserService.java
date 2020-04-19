package com.sbz.agro.service;

import com.sbz.agro.dto.UserRegistrationDto;
import com.sbz.agro.model.User;

public interface UserService {

	void save(User user);
	User findByUsername(String username);
	void registerNewUser(UserRegistrationDto userDto);
}
