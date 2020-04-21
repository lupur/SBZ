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

import com.sbz.agro.dto.DiseaseDto;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.DiseaseService;

@RestController
@RequestMapping("/diseases")
@SuppressWarnings("rawtypes")
public class DiseaseController {

    @Autowired
    AuthService authService;

    @Autowired
    DiseaseService diseaseService;

    @PostMapping()
    public ResponseEntity addDisease(@RequestHeader("Token") String token, @RequestBody DiseaseDto disease) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (diseaseService.addDisease(disease.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Already exists");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDisease(@RequestHeader("Token") String token, @PathVariable Long id) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (diseaseService.removeDisease(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity updateDisease(@RequestHeader("Token") String token, @RequestBody DiseaseDto disease) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (diseaseService.updateDisease(disease)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getDisease(@RequestHeader("Token") String token, @PathVariable Long id) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        DiseaseDto c = diseaseService.getDiseaseDto(id);

        if (c != null) {
            return ResponseEntity.ok().body(c);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity getDiseases(@RequestHeader("Token") String token) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<DiseaseDto> diseases = diseaseService.getAllDiseasesDto();

        return ResponseEntity.ok().body(diseases);
    }
}
