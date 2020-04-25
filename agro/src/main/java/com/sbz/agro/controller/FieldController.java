package com.sbz.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.FieldService;

@RestController
@RequestMapping("fields")
@SuppressWarnings("rawtypes")
public class FieldController {

    @Autowired
    AuthService authService;

    @Autowired
    FieldService fieldService;

    @Autowired
    TokenUtil tokenUtil;

    @GetMapping
    public ResponseEntity getAllFields(@RequestHeader("Token") String token) {
        if (authService.isAdmin(token)) {
            return ResponseEntity.ok().body(fieldService.getAllFields());
        } else if (authService.isLoggedIn(token)) {
            String username = tokenUtil.getUsernameFromToken(token);

            return ResponseEntity.ok().body(fieldService.getUsersFields(username));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
