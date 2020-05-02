package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.GetUserDto;
import com.sbz.agro.dto.LoginResponseDto;
import com.sbz.agro.dto.UserLoginDto;
import com.sbz.agro.dto.UserRegistrationDto;
import com.sbz.agro.enums.Role;
import com.sbz.agro.model.User;
import com.sbz.agro.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    private KieContainer kieContainer;

    @Autowired
    private AuthService authService;

    @Override
    public boolean registerNewUser(UserRegistrationDto userDto) {

        if (userDto == null || userRepository.findByUsername(userDto.getUsername()) != null)
            return false;

        User newUser = new User();
        newUser.setRole(Role.USER);
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(newUser);

//        KieSession kieSession = kieContainer.newKieSession();
//        kieSession.insert(newUser);
//        kieSession.fireAllRules();
//        kieSession.dispose();

        return true;
    }

    @Override
    public LoginResponseDto login(UserLoginDto user) {
    	String token = authService.login(user.getUsername(), user.getPassword());
    	if(token == null) return null;
  
    	User u = userRepository.findByUsername(user.getUsername());
    	if(u == null) return null;
  
    	return new LoginResponseDto(u.getId(), token, u.getRole().toString());
    }

    @Override
    public void logout(String token) {
        authService.logout(token);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUserLoggedIn(String token) {
        return authService.isLoggedIn(token);
    }

    @Override
    public List<GetUserDto> getAllUsers() {
        List<User> usersDb = userRepository.findAll();
        List<GetUserDto> users = new ArrayList<GetUserDto>();
        for (User u : usersDb) {
            GetUserDto user = new GetUserDto();
            user.setUsername(u.getUsername());
            user.setRole(u.getRole());
            user.setId(u.getId());
            users.add(user);
        }

        return users;
    }

	@Override
	public boolean setRole(Long id, Role role) {
		try {
			User u = userRepository.findById(id).get();
			u.setRole(role);
			userRepository.save(u);
			return true;
		} catch(Exception e)
		{
			return false;
		}
	}

}
