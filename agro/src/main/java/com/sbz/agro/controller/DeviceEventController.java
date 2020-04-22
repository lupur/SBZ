package com.sbz.agro.controller;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.event.DeviceEvent;

@RestController
@RequestMapping("/events/devices")
public class DeviceEventController {
	
	@Autowired
	private KieContainer kieContainer;
	
	private KieSession kieSession;

    @PutMapping
    public ResponseEntity updateCrop(@RequestBody DeviceEvent deviceEvent) {
    	if(kieSession == null) kieSession = kieContainer.newKieSession("deviceMonitorSession");
    	kieSession.insert(new DeviceEvent(deviceEvent));
    	kieSession.fireAllRules();
        return ResponseEntity.ok().build();
    }

}
