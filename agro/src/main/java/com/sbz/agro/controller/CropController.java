package com.sbz.agro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.CropDto;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.CropService;

@RestController
@RequestMapping("/crops")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CropController {

    @Autowired
    AuthService authService;

    @Autowired
    CropService cropService;

    @PostMapping()
    public ResponseEntity addCrop(@RequestHeader("Token") String token, @RequestBody CropDto crop) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (cropService.addCrop(crop.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Already exists");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCrop(@RequestHeader("Token") String token, @PathVariable Long id) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (cropService.removeCrop(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity updateCrop(@RequestHeader("Token") String token, @RequestBody CropDto crop) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (cropService.updateCrop(crop)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCrop(@RequestHeader("Token") String token, @PathVariable Long id) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CropDto c = cropService.getCropDto(id);

        if (c != null) {
            return ResponseEntity.ok().body(c);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity getCrops(@RequestHeader("Token") String token) {
        if (!authService.isLoggedIn(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<CropDto> crops = cropService.getAllCropsDto();

        return ResponseEntity.ok().body(crops);
    }
}
