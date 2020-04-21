package com.sbz.agro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.SymptomDto;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.SymptomService;

@RestController
@RequestMapping("/symptoms")
@SuppressWarnings("rawtypes")
public class SymptomController {

    @Autowired
    AuthService authService;

    @Autowired
    SymptomService symptomService;

    @PostMapping()
    public ResponseEntity addSymptom(@RequestHeader("Token") String token, @RequestBody SymptomDto symptom) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (symptomService.addSymptom(symptom.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Already exists");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSymptom(@RequestHeader("Token") String token, @PathVariable Long id) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (symptomService.removeSymptom(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity updateSymptom(@RequestHeader("Token") String token, @RequestBody SymptomDto symptom) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (symptomService.updateSymptom(symptom)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getSymptom(@RequestHeader("Token") String token, @PathVariable Long id) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        SymptomDto c = symptomService.getSymptomDto(id);

        if (c != null) {
            return ResponseEntity.ok().body(c);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity getSymptoms(@RequestHeader("Token") String token) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<SymptomDto> symptoms = symptomService.getAllSymptomsDto();

        return ResponseEntity.ok().body(symptoms);
    }
}
