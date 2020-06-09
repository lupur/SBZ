package com.sbz.agro.controller;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.ReadingDto;
import com.sbz.agro.model.Reading;
import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.DeviceService;
import com.sbz.agro.service.ReadingService;

@RestController
@RequestMapping("/reading")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReadingController {

    @Autowired
    ReadingService readingService;

    @Autowired
    AuthService authService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    KieSession kieIrrigationSession;

    @PostMapping()
    public ResponseEntity addReading(@RequestBody ReadingDto newReading) {
        if (newReading == null || !newReading.isValid()) {
            return ResponseEntity.badRequest().build();
        }
        Reading reading = readingService.addReading(newReading);
        if (reading == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        kieIrrigationSession.getAgenda().getAgendaGroup("irrigation").setFocus();
        kieIrrigationSession.insert(reading);

//        EntryPoint irrigationStream = kieIrrigationSession.getEntryPoint("Irrigation");
        kieIrrigationSession.insert(reading);
//        kieIrrigationSession.fireAllRules();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{serialNo}/{name}")
    public ResponseEntity getReadings(@RequestHeader("Token") String token, @PathVariable String serialNo,
            @PathVariable String name) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = tokenUtil.getUsernameFromToken(token);

        if (!deviceService.userOwnsDevice(serialNo, username) && !authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!deviceService.isValidReadingName(name)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(readingService.getReadingsBySerialNoAndName(serialNo, name));
    }
}
