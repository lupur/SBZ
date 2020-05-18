package com.sbz.agro;

import java.util.Arrays;
import java.util.Date;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.enums.DeviceReadingTypes;
import com.sbz.agro.enums.ReadingValues;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.model.Reading;
import com.sbz.agro.model.User;

public class TestDrools {
	
	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz","agro-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		KieSession kieSession = kContainer.newKieSession("deviceMonitorSession");
		insertDevices(kieSession);
    	
		DeviceArray array1 = new DeviceArray();
		array1.setId(1l);
    	Device s12 = new Device("S12000", DeviceDetails.MOISTURE_SENSOR, array1, 2);
    	s12.setId(1l);
    	Device s13 = new Device("S13000", DeviceDetails.MOISTURE_SENSOR, array1, 3);
    	s13.setId(2l);
    	Device v12 = new Device("V12000", DeviceDetails.VALVE, array1, 2);
    	v12.setId(4l);
    	Device pump1 = new Device("P10000", DeviceDetails.PUMP, array1, 0);
    	pump1.setId(0l);
    	v12.setId(5l);
    	array1.setDevices(Arrays.asList(s12, v12, pump1));
    	
    	
    	Reading r1 = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading r111 = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading ri = new Reading(v12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading r2 = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), new Date());
    	Reading r3 = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading r4 = new Reading(s12, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading r5 = new Reading(s13, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading rPump = new Reading(pump1, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), new Date());
    	Reading rPumpOk = new Reading(pump1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), new Date());
    	
//    	kieSession.insert(array1);
//    	kieSession.fireAllRules();
    	kieSession.insert(r1);
    	kieSession.insert(ri);
    	kieSession.insert(rPump);

    	
    	kieSession.fireAllRules();
    	
    	Thread.sleep(11000l);
    	kieSession.insert(r3);
    	kieSession.fireAllRules();
    	Thread.sleep(2000l);
    	kieSession.insert(r4);
    	kieSession.insert(r5);
    	kieSession.fireAllRules();
    	Thread.sleep(5000l);
    	kieSession.insert(rPumpOk);
    	kieSession.fireAllRules();
    	Thread.sleep(2000l);
    	kieSession.insert(r2);
    	kieSession.fireAllRules();
    	Thread.sleep(1000l);
    	kieSession.insert(r111);
    	kieSession.fireAllRules();

    	
//    	Reading s121Reading = new Reading();
//    	s121Reading.setName(DeviceReadingTypes.MOISTURE.name());
//    	s121Reading.setDevice(s12);
//    	s121Reading.setValue("35.55"); 	
//    	
//    	
//    	Reading s122Reading = new Reading();
//    	s122Reading.setName(DeviceReadingTypes.MOISTURE.name());
//    	s122Reading.setDevice(s12);
//    	s122Reading.setValue("64.22"); 	
//    	
//		DeviceArray array2 = new DeviceArray();
//		array2.setId(2l);
//    	Device s23 = new Device("S23000", DeviceDetails.SENSOR, array2, 3);
//    	Reading s231Reading = new Reading();
//    	s231Reading.setName(DeviceReadingTypes.MOISTURE.name());
//    	s231Reading.setDevice(s23);
//    	s231Reading.setValue("35.55");
//    	s231Reading.setValue("35.55"); 	
//    	
//    	Reading s232Reading = new Reading();
//    	s232Reading.setName(DeviceReadingTypes.MOISTURE.name());
//    	s232Reading.setDevice(s23);
//    	s232Reading.setValue("64.22"); 	
//    	
//    	
//		DeviceArray array3 = new DeviceArray();
//		array3.setId(3l);
//    	Device s31 = new Device("S31000", DeviceDetails.SENSOR, array3, 1);
//    	Reading s31Reading = new Reading();
//    	s31Reading.setName(DeviceReadingTypes.MOISTURE.name());
//    	s31Reading.setDevice(s31);
//    	s31Reading.setValue("64.22"); 	
//    	
//    	kieSession.insert(s121Reading);
//    	kieSession.insert(s122Reading);
//    	kieSession.insert(s231Reading);
//    	kieSession.insert(s232Reading);
//    	kieSession.insert(s31Reading);
//    	kieSession.fireAllRules();
	}
	
	public static void insertDevices(KieSession kieSession) {
		DeviceArray array1 = new DeviceArray();
		array1.setId(1l);
		Device s11 = new Device("S11000", DeviceDetails.MOISTURE_SENSOR, array1, 1);
		s11.setId(1l);
		Device s12 = new Device("S12000", DeviceDetails.MOISTURE_SENSOR, array1, 2);
		s12.setId(2l);
		Device s13 = new Device("S13000", DeviceDetails.MOISTURE_SENSOR, array1, 3);
		s13.setId(3l);
		Device v11 = new Device("V11000", DeviceDetails.VALVE, array1, 1);
		v11.setId(4l);
		Device v12 = new Device("V12000", DeviceDetails.VALVE, array1, 2);
		v12.setId(5l);
		Device v13 = new Device("V13000", DeviceDetails.VALVE, array1, 3);
		v13.setId(6l);
		Device pump1 = new Device("P10000", DeviceDetails.PUMP, array1, 0);
		pump1.setId(0l);
		
		DeviceArray array2 = new DeviceArray();
		array2.setId(2l);
		Device s21 = new Device("S21000", DeviceDetails.MOISTURE_SENSOR, array2, 1);
		s21.setId(7l);
		Device s22 = new Device("S22000", DeviceDetails.MOISTURE_SENSOR, array2, 2);
		s22.setId(8l);
		Device s23 = new Device("S23000", DeviceDetails.MOISTURE_SENSOR, array2, 3);
		s23.setId(9l);
		Device v21 = new Device("V21000", DeviceDetails.VALVE, array2, 1);
		v21.setId(10l);
		Device v22 = new Device("V22000", DeviceDetails.VALVE, array2, 2);
		v22.setId(11l);
		Device v23 = new Device("V23000", DeviceDetails.VALVE, array2, 3);
		v23.setId(12l);
		
		Field field1 = new Field();
		User user = new User();
		user.setId(1l);
		field1.setId(1l);
		field1.addDeviceArray(array1);
		field1.setOwner(user);
//		field1.setMoistureLowerThreshold(Double.valueOf(20));
//		field1.setMoistureUpperThreshold(Double.valueOf(60));
		array1.setField(field1);
		
		Field field2 = new Field();
		field2.setId(2l);
		field2.addDeviceArray(array2);
//		field2.setMoistureLowerThreshold(Double.valueOf(40));
//		field2.setMoistureUpperThreshold(Double.valueOf(80));
		array2.setField(field2);
		
		kieSession.insert(s11);
		kieSession.insert(s12);
		kieSession.insert(s13);
		kieSession.insert(s21);
		kieSession.insert(s22);
		kieSession.insert(s23);
		kieSession.insert(v11);
		kieSession.insert(v12);
		kieSession.insert(v13);
		kieSession.insert(v21);
		kieSession.insert(v22);
		kieSession.insert(v23);
		kieSession.insert(pump1);
		
		kieSession.insert(array1);
		kieSession.insert(array2);
		
		kieSession.insert(field1);
		kieSession.insert(field2);
	
		kieSession.fireAllRules();
	}

}
