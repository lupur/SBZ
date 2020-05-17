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

import com.sbz.agro.dto.DeviceDto;
import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.DeviceArrayService;
import com.sbz.agro.service.DeviceService;
import com.sbz.agro.service.FieldService;

@RestController
@RequestMapping("fields/{fieldId}/arrays/{arrayId}/devices")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeviceController {

    @Autowired
    AuthService authService;

    @Autowired
    DeviceArrayService arrayService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    DeviceService deviceService;

    @Autowired
    FieldService fieldService;

    @GetMapping
    public ResponseEntity getDevices(@RequestHeader("Token") String token, @PathVariable Long fieldId,
            @PathVariable Long arrayId) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!arrayService.arrayExists(fieldId)) {
            return ResponseEntity.notFound().build();
        }

        if (arrayService.getArrayOfField(fieldId, arrayId) == null) {
            return ResponseEntity.notFound().build();
        }

        String username = tokenUtil.getUsernameFromToken(token);
        if (!authService.isAdmin(token) && !fieldService.userOwnsField(username, fieldId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(deviceService.getArrayDevices(arrayId));
    }

    @GetMapping(value = "/{deviceId}")
    public ResponseEntity getDevice(@RequestHeader("Token") String token, @PathVariable Long fieldId,
            @PathVariable Long arrayId, @PathVariable Long deviceId) {
        if (!authService.atLeastUser(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (arrayService.getArrayOfField(fieldId, arrayId) == null) {
            return ResponseEntity.notFound().build();
        }

        if (!arrayService.arrayExists(arrayId) || !deviceService.deviceInArray(arrayId, deviceId)) {
            return ResponseEntity.notFound().build();
        }

        String username = tokenUtil.getUsernameFromToken(token);
        if (!authService.isAdmin(token) && !fieldService.userOwnsField(username, fieldId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(deviceService.getDeviceFromArray(arrayId, deviceId));
    }

    @PostMapping
    public ResponseEntity addDevice(@RequestHeader("Token") String token, @PathVariable Long fieldId,
            @PathVariable Long arrayId, @RequestBody DeviceDto newDevice) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (arrayService.getArrayOfField(fieldId, arrayId) == null) {
            return ResponseEntity.notFound().build();
        }

        newDevice.setArrayId(arrayId);

        if (!arrayService.arrayExists(newDevice.getArrayId())) {
            return ResponseEntity.notFound().build();
        }

        if (newDevice.getType() == DeviceDetails.PUMP && newDevice.getPosition() != 0) {
            return ResponseEntity.badRequest().body("Pump position must be 0");
        } else if (newDevice.getType() != DeviceDetails.PUMP && newDevice.getType() != DeviceDetails.RAIN_SENSOR && newDevice.getPosition() == 0) {
            return ResponseEntity.badRequest().body("Only Pump and rain sensor position can be 0");
        }

        if (!deviceService.isPositionAvailable(newDevice.getArrayId(), newDevice.getPosition(), newDevice.getType())) {
            return ResponseEntity.badRequest().body("Position not available");
        }

        if (deviceService.addDevice(newDevice)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{deviceId}")
    public ResponseEntity removeDevice(@RequestHeader("Token") String token, @PathVariable Long fieldId,
            @PathVariable Long arrayId, @PathVariable Long deviceId) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (arrayService.getArrayOfField(fieldId, arrayId) == null) {
            return ResponseEntity.notFound().build();
        }

        if (!arrayService.arrayExists(arrayId) || !deviceService.deviceInArray(arrayId, deviceId)) {
            return ResponseEntity.notFound().build();
        }

        if (deviceService.removeDevice(deviceId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
