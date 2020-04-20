package com.sbz.agro.service;

import java.util.HashSet;

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

    @Override
    public void registerNewUser(UserRegistrationDto userDto) {
        if(userDto==null && userRepository.findByUsername(userDto.getUsername())!=null) return;

        User newUser = new User();
        newUser.setRole(Role.USER);
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        userRepository.save(newUser);
        
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(newUser);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
