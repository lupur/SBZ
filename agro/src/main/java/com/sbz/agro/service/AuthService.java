package com.sbz.agro.service;

public interface AuthService {

    public String login(String username, String password);

    boolean isLoggedIn(String token);

    void logout(String token);

    boolean isAdmin(String token);

    boolean isExpert(String token);

    boolean isUser(String token);

    boolean atLeastUser(String token);

    boolean atLeastExpert(String token);

    boolean atLeastAdmin(String token);
}
