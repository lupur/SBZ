package com.sbz.agro;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.enums.DeviceReadingTypes;
import com.sbz.agro.enums.ReadingValues;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.model.Reading;
import com.sbz.agro.model.User;

@SpringBootTest
public class ErrorReportsTest {
	
	Date initDate = new Date();
	Date after5min = new Date(initDate.getTime() + 5*60*1000);
	Date after10min = new Date(initDate.getTime() + 10*60*1000);
	Date after15min = new Date(initDate.getTime() + 15*60*1000);
	Date after30min = new Date(initDate.getTime() + 30*60*1000);
	
	Device s11 = null;
	Device s12 = null;
	Device s13 = null;
	Device v11 = null;
	Device v12 = null;
	Device v13 = null;
	Device p1 = null;
	DeviceArray a1 = null;
	
	Device s21 = null;
	Device s22 = null;
	Device v21 = null;
	Device v22 = null;
	Device p2 = null;
	DeviceArray a2 = null;
	
	DeviceArray a3 = null;
	
	Field f = null;
	
    // Setting up KIE
    KieSession kieSession; 
    KieBaseConfiguration config;
	
	@BeforeEach
	public void setup() {
		
	    KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz","agro-kjar", "0.0.1-SNAPSHOT"));
	    KieScanner kScanner = ks.newKieScanner(kContainer);
	    kieSession = kContainer.newKieSession("deviceMonitorSession");
	    config = KieServices.Factory.get().newKieBaseConfiguration();
		
		User user = new User();
		user.setId(1l);
		f = new Field();
		f.setId(1l);
		f.setOwner(user);
		
		a1 = new DeviceArray();
		a1.setId(1l);
		a1.setField(f);
		p1 = new Device(0l, "P10000", DeviceDetails.PUMP, a1, 0);
		s11 = new Device(1l, "S11000", DeviceDetails.MOISTURE_SENSOR, a1, 1);
		s12 = new Device(2l, "S12000", DeviceDetails.MOISTURE_SENSOR, a1, 2);
		s13 = new Device(3l, "S13000", DeviceDetails.MOISTURE_SENSOR, a1, 3);
		v11 = new Device(4l, "V11000", DeviceDetails.VALVE, a1, 1);
		v12 = new Device(5l, "V12000", DeviceDetails.VALVE, a1, 2);
		v13 = new Device(6l, "V13000", DeviceDetails.VALVE, a1, 3);

		a2 = new DeviceArray();
		a2.setId(2l);
		a2.setField(f);
		p2 = new Device(10l, "P20000", DeviceDetails.PUMP, a2, 0);
		s21 = new Device(11l, "S21000", DeviceDetails.MOISTURE_SENSOR, a2, 1);
		s22 = new Device(12l, "S22000", DeviceDetails.MOISTURE_SENSOR, a2, 2);
		v21 = new Device(13l, "V21000", DeviceDetails.VALVE, a2, 1);
		v22 = new Device(14l, "V22000", DeviceDetails.VALVE, a2, 2);
		
		a3 = new DeviceArray();
		a3.setId(3l);
		a3.setField(f);
		
		f.addDeviceArray(a1);
		f.addDeviceArray(a2);
		f.addDeviceArray(a3);
		
		kieSession.insert(s11);
		kieSession.insert(s12);
		kieSession.insert(s13);
		kieSession.insert(s21);
		kieSession.insert(s22);
		kieSession.insert(v11);
		kieSession.insert(v12);
		kieSession.insert(v13);
		kieSession.insert(v21);
		kieSession.insert(v22);
		kieSession.insert(p1);
		kieSession.insert(p2);
		kieSession.insert(a1);
		kieSession.insert(a2);
		kieSession.insert(a3);
		kieSession.insert(f);
		
		kieSession.fireAllRules();
	}
	
	@Test
	public void causeDeviceSetError() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorInit = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST causeDeviceSetError:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.insert(s12errorInit);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void ignoreDeviceSetOk() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s11okAfter5min = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after5min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST ignoreDeviceSetOk:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s11okAfter5min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void fixDeviceSetError() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s11okAfter15min = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after15min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST fixDeviceSetError:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s11okAfter15min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void causePumpAndArrayError() {
		Reading p1errorInit = new Reading(p1, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
		
		System.out.println("**************************");
		System.out.println("TEST causePumpAndArrayError:");
		System.out.println("**************************");
    	
    	kieSession.insert(p1errorInit);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void fixPumpAndArrayError() {
		Reading p1errorInit = new Reading(p1, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
		Reading p1okAfter15Min = new Reading(p1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after15min);
		
		System.out.println("**************************");
		System.out.println("TEST fixPumpAndArrayError:");
		System.out.println("**************************");
    	
    	kieSession.insert(p1errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(p1okAfter15Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void causeArrayErrorBecauseDeviceSet() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST causeArrayErrorBecauseDeviceSet:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void fixArrayErrorNoPumpError() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading s11okAfter15Min = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after15min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST fixArrayErrorNoPumpError:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.fireAllRules();
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(s11okAfter15Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void arrayErrorRemainsBecauseOfPumpError() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading p1errorAfter10Min = new Reading(p1, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading s11okAfter15Min = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after15min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST arrayErrorRemainsBecauseOfPumpError:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.fireAllRules();
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p1errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(s11okAfter15Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void fixArrayErrorWithPumpError() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading p1errorAfter10Min = new Reading(p1, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading s11okAfter15Min = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after15min);
    	Reading p1errorAfter30Min = new Reading(p1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after30min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST fixArrayErrorWithPumpError:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.fireAllRules();
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p1errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(s11okAfter15Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p1errorAfter30Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void causeFieldError() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading p2errorAfter10Min = new Reading(p2, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST causeFieldError:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.fireAllRules();
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p2errorAfter10Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void fixFieldErrorByDeviceSet() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading p2errorAfter10Min = new Reading(p2, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading s11okAfter15Min = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after15min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST fixFieldErrorByDeviceSet:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.fireAllRules();
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p2errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(s11okAfter15Min);
    	kieSession.fireAllRules();
	}
	
	@Test
	public void fixFieldErrorByPump() {
		Reading s11errorInit = new Reading(s11, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), initDate);
    	Reading s12errorAfter5Min = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after5min);
    	Reading v12errorAfter10Min = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading p2errorAfter10Min = new Reading(p2, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), after10min);
    	Reading p2errorAfter30Min = new Reading(p2, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), after30min);
    	
    	System.out.println("**************************");
    	System.out.println("TEST fixFieldErrorByPump:");
    	System.out.println("**************************");
    	
    	kieSession.insert(s11errorInit);
    	kieSession.fireAllRules();
    	kieSession.insert(s12errorAfter5Min);
    	kieSession.fireAllRules();
    	kieSession.insert(v12errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p2errorAfter10Min);
    	kieSession.fireAllRules();
    	kieSession.insert(p2errorAfter30Min);
    	kieSession.fireAllRules();
	}

}
