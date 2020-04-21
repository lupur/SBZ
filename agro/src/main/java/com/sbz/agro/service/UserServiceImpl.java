package com.sbz.agro.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Autowired
    private KieContainer kieContainer;

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

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(newUser);
        kieSession.fireAllRules();
        kieSession.dispose();

        return true;
    }

    @Override
    public String login(String username, String password) {
        return authService.login(username, password);
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

}
