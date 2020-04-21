package com.sbz.agro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sbz.agro.enums.Role;
import com.sbz.agro.model.User;
import com.sbz.agro.repository.UserRepository;
import com.sbz.agro.security.TokenUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private DaoAuthenticationProvider authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null)
            return null;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (!usernamePasswordAuthenticationToken.isAuthenticated())
            return null;

        User u = userRepository.findByUsername(username);
        if (u == null)
            return null;
        String token = tokenUtil.generateToken(userDetails);
        u.AddToken(token);
        userRepository.save(u);
        return token;

    }

    @Override
    public boolean isLoggedIn(String token) {
        User u = getUserFromToken(token);
        if (u != null && u.HasToken(token)) {
            return true;
        }
        return false;
    }

    @Override
    public void logout(String token) {
        User u = getUserFromToken(token);
        if (u != null) {
            u.RemoveToken(token);
            userRepository.save(u);
        }
    }

    @Override
    public boolean isAdmin(String token) {
        User u = getUserFromToken(token);
        if (u != null) {
            if (u.getRole() == Role.ADMIN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExpert(String token) {
        User u = getUserFromToken(token);
        if (u != null) {
            if (u.getRole() == Role.EXPERT) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUser(String token) {
        User u = getUserFromToken(token);
        if (u != null) {
            if (u.getRole() == Role.USER) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean atLeastAdmin(String token) {
        User u = getUserFromToken(token);
        if (u != null) {
            if (u.getRole() == Role.ADMIN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean atLeastExpert(String token) {
        User u = getUserFromToken(token);
        if (u != null) {
            if (u.getRole() == Role.ADMIN || u.getRole() == Role.EXPERT) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean atLeastUser(String token) {
        return isLoggedIn(token);
    }

    private User getUserFromToken(String token) {
        String username = tokenUtil.getUsernameFromToken(token);
        return userRepository.findByUsername(username);
    }
}
