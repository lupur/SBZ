package com.sbz.agro.controller;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.model.Device;
import com.sbz.agro.service.AuthService;
import com.sbz.agro_kjar.services.TemplateService;

@RestController
@RequestMapping("template")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DroolsTemplateController {

    KieSession kieSession;
    List<String> supportedDevices;

    @Autowired
    AuthService authService;

    @PutMapping("/add/{device}")
    public ResponseEntity addSupportedDevice(@RequestHeader("Token") String token, @PathVariable String device) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (device == null)
            return ResponseEntity.badRequest().build();

        if (supportedDevices == null)
            supportedDevices = new ArrayList<>();

        boolean alreadyExists = false;
        for (String d : supportedDevices) {
            if (d.equals(device)) {
                alreadyExists = true;
            }
        }
        if (!alreadyExists) {
            supportedDevices.add(device);
            kieSession = TemplateService.GenerateRules(supportedDevices);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{device}")
    public ResponseEntity removeSupportedDevice(@RequestHeader("Token") String token, @PathVariable String device) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (device == null)
            return ResponseEntity.badRequest().build();

        if (supportedDevices == null)
            ResponseEntity.ok().build();

        boolean alreadyExists = false;
        for (String d : supportedDevices) {
            if (d.equals(device)) {
                supportedDevices.remove(d);
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists) {
            return ResponseEntity.badRequest().build();
        }

        kieSession = TemplateService.GenerateRules(supportedDevices);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{device}")
    public ResponseEntity isDeviceSupported(@RequestHeader("Token") String token, @PathVariable String device) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (device == null)
            return ResponseEntity.badRequest().build();

        if (kieSession == null)
            return ResponseEntity.ok(false);
        Device d = new Device();
        d.setId(-1l);
        d.setSerialNo(device);
        kieSession.insert(d);

        kieSession.fireAllRules();

        boolean ret = false;
        if (d.getId() == 200)
            ret = true;

        return ResponseEntity.ok(ret);
    }
}
