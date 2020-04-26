package com.sbz.agro;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.enums.DeviceReadingTypes;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.model.Reading;

public class TestDrools {
	
	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz","agro-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		KieSession kieSession = kContainer.newKieSession("deviceMonitorSession");
		insertDevices(kieSession);
    	
		DeviceArray array1 = new DeviceArray();
		array1.setId(1l);
    	Device s12 = new Device("S12000", DeviceDetails.SENSOR, array1, 2);
    	Reading s121Reading = new Reading();
    	s121Reading.setName(DeviceReadingTypes.MOISTURE.name());
    	s121Reading.setDevice(s12);
    	s121Reading.setValue(Double.valueOf("35.55")); 	
    	
    	Reading s122Reading = new Reading();
    	s122Reading.setName(DeviceReadingTypes.MOISTURE.name());
    	s122Reading.setDevice(s12);
    	s122Reading.setValue(Double.valueOf("64.22")); 	
    	
		DeviceArray array2 = new DeviceArray();
		array2.setId(2l);
    	Device s23 = new Device("S23000", DeviceDetails.SENSOR, array2, 3);
    	Reading s231Reading = new Reading();
    	s231Reading.setName(DeviceReadingTypes.MOISTURE.name());
    	s231Reading.setDevice(s23);
    	s231Reading.setValue("35.55");
    	s231Reading.setValue(Double.valueOf("35.55")); 	
    	
    	Reading s232Reading = new Reading();
    	s232Reading.setName(DeviceReadingTypes.MOISTURE.name());
    	s232Reading.setDevice(s23);
    	s232Reading.setValue(Double.valueOf("64.22")); 	
    	
    	
		DeviceArray array3 = new DeviceArray();
		array3.setId(3l);
    	Device s31 = new Device("S31000", DeviceDetails.SENSOR, array3, 1);
    	Reading s31Reading = new Reading();
    	s31Reading.setName(DeviceReadingTypes.MOISTURE.name());
    	s31Reading.setDevice(s31);
    	s31Reading.setValue(Double.valueOf("64.22")); 	
    	
    	kieSession.insert(s121Reading);
    	kieSession.insert(s122Reading);
    	kieSession.insert(s231Reading);
    	kieSession.insert(s232Reading);
    	kieSession.insert(s31Reading);
    	kieSession.fireAllRules();
	}
	
	public static void insertDevices(KieSession kieSession) {
		DeviceArray array1 = new DeviceArray();
		array1.setId(1l);
		Device s11 = new Device("S11000", DeviceDetails.SENSOR, array1, 1);
		Device s12 = new Device("S12000", DeviceDetails.SENSOR, array1, 2);
		Device s13 = new Device("S13000", DeviceDetails.SENSOR, array1, 3);
		Device v11 = new Device("V11000", DeviceDetails.VALVE, array1, 1);
		Device v12 = new Device("V12000", DeviceDetails.VALVE, array1, 2);
		Device v13 = new Device("V13000", DeviceDetails.VALVE, array1, 3);
		
		DeviceArray array2 = new DeviceArray();
		array2.setId(2l);
		Device s21 = new Device("S21000", DeviceDetails.SENSOR, array2, 1);
		Device s22 = new Device("S22000", DeviceDetails.SENSOR, array2, 2);
		Device s23 = new Device("S23000", DeviceDetails.SENSOR, array2, 3);
		Device v21 = new Device("V21000", DeviceDetails.VALVE, array2, 1);
		Device v22 = new Device("V22000", DeviceDetails.VALVE, array2, 2);
		Device v23 = new Device("V23000", DeviceDetails.VALVE, array2, 3);
		
		Field field1 = new Field();
		field1.setId(1l);
		field1.addDeviceArray(array1);
		field1.setMoistureLowerThreshold(Double.valueOf(20));
		field1.setMoistureUpperThreshold(Double.valueOf(60));
		array1.setField(field1);
		
		Field field2 = new Field();
		field2.setId(2l);
		field2.addDeviceArray(array2);
		field2.setMoistureLowerThreshold(Double.valueOf(40));
		field2.setMoistureUpperThreshold(Double.valueOf(80));
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
		
		kieSession.insert(array1);
		kieSession.insert(array2);
		
		kieSession.insert(field1);
		kieSession.insert(field2);
	
		kieSession.fireAllRules();
	}

}
