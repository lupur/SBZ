package com.sbz.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.DeviceArrayService;
import com.sbz.agro.service.FieldService;

@RestController
@RequestMapping("fields/{fieldId}/arrays")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeviceArrayController {

    @Autowired
    AuthService authService;

    @Autowired
    DeviceArrayService arrayService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    FieldService fieldService;

    @GetMapping
    public ResponseEntity getArrays(@RequestHeader("Token") String token, @PathVariable Long fieldId) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!arrayService.arrayExists(fieldId)) {
            return ResponseEntity.notFound().build();
        }

        String username = tokenUtil.getUsernameFromToken(token);
        if (!authService.isAdmin(token) && !fieldService.userOwnsField(username, fieldId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(arrayService.getFieldArrays(fieldId));
    }

    @GetMapping(value = "/{arrayId}")
    public ResponseEntity getArray(@RequestHeader("Token") String token, @PathVariable Long fieldId,
            @PathVariable Long arrayId) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!arrayService.arrayExists(arrayId)) {
            return ResponseEntity.notFound().build();
        }

        String username = tokenUtil.getUsernameFromToken(token);
        if (!authService.isAdmin(token) && !fieldService.userOwnsField(username, fieldId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(arrayService.getArrayOfField(fieldId, arrayId));
    }

//    @PostMapping
//    public ResponseEntity addArray(@RequestHeader("Token") String token, @PathVariable Long fieldId) {
//        if (!authService.isAdmin(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        if (arrayService.addArray(fieldId)) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
    
    @PostMapping
    public ResponseEntity addArra(@RequestHeader("Token") String token, @PathVariable Long fieldId, @RequestParam("pumpEUI") String pumpEUI, @RequestParam("rainEUI") String rainEUI) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (arrayService.addArray(fieldId, pumpEUI, rainEUI)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{arrayId}")
    public ResponseEntity addSet(@RequestHeader("Token") String token, @PathVariable Long fieldId, @PathVariable Long arrayId, @RequestParam("moistureEUI") String moistureEUI, @RequestParam("valveEUI") String valveEUI) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (arrayService.addSet(arrayId, moistureEUI, valveEUI)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{arrayId}")
    public ResponseEntity editArray(@RequestHeader("Token") String token, @PathVariable Long fieldId, @PathVariable Long arrayId, @RequestParam("pumpEUI") String pumpEUI, @RequestParam("rainEUI") String rainEUI) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (arrayService.editArray(arrayId, pumpEUI, rainEUI)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{arrayId}/sets/{position}")
    public ResponseEntity editSet(@RequestHeader("Token") String token, @PathVariable Long fieldId, @PathVariable Long arrayId, @PathVariable Integer position, @RequestParam("moistureEUI") String moistureEUI, @RequestParam("valveEUI") String valveEUI) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (arrayService.editSet(arrayId, position, moistureEUI, valveEUI)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{arrayId}")
    public ResponseEntity removeArray(@RequestHeader("Token") String token,@PathVariable Long fieldId, @PathVariable Long arrayId) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (arrayService.removeArray(arrayId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping(value = "/{arrayId}/sets/{position}")
    public ResponseEntity removeSet(@RequestHeader("Token") String token,@PathVariable Long fieldId, @PathVariable Long arrayId, @PathVariable Integer position) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (arrayService.removeSet(arrayId, position)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
