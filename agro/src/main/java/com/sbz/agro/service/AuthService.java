package com.sbz.agro.service;

public interface AuthService {

    public String login(String username, String password);

    boolean isLoggedIn(String token);

    void logout(String token);
}
