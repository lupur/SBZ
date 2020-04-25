package com.sbz.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.DeviceArrayService;

@RestController
@RequestMapping("fields/{fieldId}/arrays")
public class DeviceArrayController {

    @Autowired
    AuthService authService;

    @Autowired
    DeviceArrayService arrayService;

    @Autowired
    TokenUtil tokenUtil;

//    @PostMapping
//    public ResponseEntity addArray(@RequestHeader("Token") String token, @PathVariable)
}
