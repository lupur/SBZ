package com.sbz.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.FieldDto;
import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.FieldService;

@RestController
@RequestMapping("fields")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FieldController {

    @Autowired
    AuthService authService;

    @Autowired
    FieldService fieldService;

    @Autowired
    TokenUtil tokenUtil;

    @GetMapping
    public ResponseEntity getFields(@RequestHeader("Token") String token) {
        if (authService.isAdmin(token)) {
            return ResponseEntity.ok().body(fieldService.getAllFields());
        } else if (authService.isLoggedIn(token)) {
            String username = tokenUtil.getUsernameFromToken(token);

            return ResponseEntity.ok().body(fieldService.getUsersFields(username));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = "{fieldId}")
    public ResponseEntity getField(@RequestHeader("Token") String token, @PathVariable Long fieldId) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!fieldService.fieldExists(fieldId)) {
            return ResponseEntity.notFound().build();
        }

        String username = tokenUtil.getUsernameFromToken(token);
        if (!authService.isAdmin(token) && !fieldService.userOwnsField(username, fieldId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(fieldService.getField(fieldId));
    }

    @PostMapping
    public ResponseEntity addField(@RequestHeader("Token") String token, @RequestBody FieldDto newField) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (fieldService.createField(newField)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{fieldId}")
    public ResponseEntity deleteField(@RequestHeader("Token") String token, @PathVariable Long fieldId) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (fieldService.removeField(fieldId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
