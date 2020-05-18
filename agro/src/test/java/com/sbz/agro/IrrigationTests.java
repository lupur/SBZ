package com.sbz.agro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.enums.DeviceReadingTypes;
import com.sbz.agro.enums.ReadingValues;
import com.sbz.agro.model.Crop;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.model.GrowthPhase;
import com.sbz.agro.model.Reading;

@SpringBootTest
public class IrrigationTests {

    // Setting up date helpers
    Date dateNow = new Date();
    Date dateHourAgo = new Date(System.currentTimeMillis() - (60 * 60 * 1000));
    Date dateTwoHoursAgo = new Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000));
    Date dateHourHalfAgo = new Date(System.currentTimeMillis() - (1800000 + 60 * 60 * 1000));
    Date dayAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
    Date twoDaysAgo = new Date(System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000));
    Date tenDaysAgo = new Date(System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000));

    // Setting up KIE
    KieServices ks = KieServices.Factory.get();
    KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz","agro-kjar", "0.0.1-SNAPSHOT"));
    KieScanner kScanner = ks.newKieScanner(kContainer);
    KieSession kieSession = kContainer.newKieSession("irrigationSession");
    KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();


    void initKie() {
        kScanner.start(10_000);
        kieSession.setGlobal("pumpCapacity", 5);
        config.setOption( EventProcessingOption.STREAM );
    }

    @Test
    void testLowMoistureLevel() {
        System.out.println("**************************");
        System.out.println("TEST Low Moisture Level:");
        System.out.println("**************************");

        initKie();

        // Initial devices setup
        GrowthPhase gf = new GrowthPhase();
        gf.setId(1L);
        gf.setMoistureLowerThreshold(20D);
        gf.setMoistureUpperThreshold(60D);
        gf.setName("Early growth phase");
        gf.setPhaseEndDay(5);
        gf.setPhaseStartDay(1);

        Crop c = new Crop();
        c.setId(1L);
        c.setName("Corn");
        c.addGrowthPhase(gf);

        Field f = new Field();
        f.setId(1L);
        f.setCrop(c);
        f.setArea(2000D);
        f.setName("Poljana");
        f.setSeedingDate(twoDaysAgo);

        DeviceArray array1 = new DeviceArray();
        array1.setField(f);

        Device moistureSensor1 = new Device("S12000", DeviceDetails.MOISTURE_SENSOR, array1, 1);
        Device rainSensor = new Device("Rain1", DeviceDetails.RAIN_SENSOR, array1, 0);
        Device valve1 = new Device("Valve1", DeviceDetails.VALVE, array1, 1);
        Device pump = new Device("Pump1", DeviceDetails.PUMP, array1, 0);

        //Reading with low moisture level
        Reading r1 = new Reading(moistureSensor1, DeviceReadingTypes.MOISTURE.name(), "10.0", new Date());
        r1.setId(1l);
        //Setting rain sensor readings
        Reading rain1 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", dateNow);
        Reading rain2 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", dateNow);
        Reading rain3 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", dateNow);
        rain1.setId(10l);
        rain2.setId(11l);
        rain3.setId(12l);

        //Setting valve status
        Reading valStatus1 = new Reading(valve1, DeviceReadingTypes.STATUS.name(), ReadingValues.ERROR.name(), dateHourAgo);
        Reading valStatus2 = new Reading(valve1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), dateNow);
        valStatus1.setId(20l);
        valStatus2.setId(21l);

        //Setting valve opened/closed state
        Reading valOpenStatus1 = new Reading(valve1, DeviceReadingTypes.STATE.name(), ReadingValues.ON.name(), dateTwoHoursAgo);
        Reading valOpenStatus2 = new Reading(valve1, DeviceReadingTypes.STATE.name(), ReadingValues.OFF.name(), dateNow);
        valOpenStatus1.setId(30l);
        valOpenStatus2.setId(31l);

        //Adding events to kie session, and firing rules
        kieSession.insert(r1);
        kieSession.insert(valve1);
        kieSession.insert(pump);

        kieSession.insert(rain1);
        kieSession.insert(rain2);
        kieSession.insert(rain3);


        kieSession.insert(valStatus1);
        kieSession.insert(valStatus2);
        kieSession.insert(valOpenStatus1);
        kieSession.insert(valOpenStatus2);

        kieSession.getAgenda().getAgendaGroup("irrigation").setFocus();
        kieSession.fireAllRules( );
    }

    @Test
    void testHighMoisture() {
        System.out.println("**************************");
        System.out.println("TEST High Moisture Level:");
        System.out.println("**************************");

        initKie();
        // Initial devices setup
        GrowthPhase gf = new GrowthPhase();
        gf.setId(1L);
        gf.setMoistureLowerThreshold(20D);
        gf.setMoistureUpperThreshold(60D);
        gf.setName("Rani razvoj");
        gf.setPhaseEndDay(5);
        gf.setPhaseStartDay(1);

        Crop c = new Crop();
        c.setId(1L);
        c.setName("Corn");
        c.addGrowthPhase(gf);

        Field f = new Field();
        f.setId(1L);
        f.setCrop(c);
        f.setArea(2000D);
        f.setName("Poljana");
        f.setSeedingDate(twoDaysAgo);

        DeviceArray array1 = new DeviceArray();
        array1.setField(f);

        Device moistureSensor1 = new Device("S12000", DeviceDetails.MOISTURE_SENSOR, array1, 1);
        Device rainSensor = new Device("Rain1", DeviceDetails.RAIN_SENSOR, array1, 0);
        Device valve1 = new Device("Valve1", DeviceDetails.VALVE, array1, 1);
        Device pump = new Device("Pump1", DeviceDetails.PUMP, array1, 0);

        //Reading with low moisture level
        Reading r1 = new Reading(moistureSensor1, DeviceReadingTypes.MOISTURE.name(), "90.0", new Date());
        r1.setId(1l);
        //Setting rain sensor readings
        Reading rain1 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", dateNow);
        Reading rain2 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", dateNow);
        Reading rain3 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", dateNow);
        rain1.setId(10l);
        rain2.setId(11l);
        rain3.setId(12l);

        //Setting valve status
        Reading valStatus1 = new Reading(valve1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), dateNow);
        Reading valStatus2 = new Reading(valve1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), dateHourAgo);
        valStatus1.setId(20l);
        valStatus2.setId(21l);

        //Setting valve opened/closed state
        Reading valOpenStatus1 = new Reading(valve1, DeviceReadingTypes.STATE.name(), ReadingValues.OFF.name(), dateTwoHoursAgo);
        Reading valOpenStatus2 = new Reading(valve1, DeviceReadingTypes.STATE.name(), ReadingValues.ON.name(), dateNow);
        valOpenStatus1.setId(30l);
        valOpenStatus2.setId(31l);

        //Adding events to kie session, and firing rules
        kieSession.insert(r1);
        kieSession.insert(valve1);
        kieSession.insert(pump);

        kieSession.insert(rain1);
        kieSession.insert(rain2);
        kieSession.insert(rain3);


        kieSession.insert(valStatus1);
        kieSession.insert(valStatus2);
        kieSession.insert(valOpenStatus1);
        kieSession.insert(valOpenStatus2);

        kieSession.getAgenda().getAgendaGroup("irrigation").setFocus();
        kieSession.fireAllRules( );
    }
    
    @Test
    void testPumpOpen() {

    	System.out.println("**************************");
        System.out.println("TEST Open pump:");
        System.out.println("**************************");
        initKie();
        DeviceArray array1 = new DeviceArray();
        DeviceArray array2 = new DeviceArray();

        Device pump1 = new Device("Pump1", DeviceDetails.PUMP, array1, 0);
        pump1.setId(11l);
        
        Device val1 = new Device("Val1", DeviceDetails.VALVE, array1, 1);
        Device val2 = new Device("Val1", DeviceDetails.VALVE, array1, 2);
        Device val3 = new Device("Val1", DeviceDetails.VALVE, array1, 3);
        Device val4 = new Device("Val1", DeviceDetails.VALVE, array1, 4);
        val1.setId(1l);
        val2.setId(2l);
        val3.setId(3l);
        val4.setId(4l);

        List<Device> devices = new ArrayList();
        devices.add(val1);
        devices.add(val2);
        devices.add(val3);
        devices.add(val4);
        devices.add(pump1);

        pump1.setArray(array1);
        array1.setDevices(devices);

        Reading pumpStatus1 = new Reading(pump1, DeviceReadingTypes.STATUS.name(), ReadingValues.OK.name(), dateNow);
        Reading pumpState = new Reading(pump1, DeviceReadingTypes.STATE.name(), ReadingValues.OFF.name(), dateNow);

        Reading valOpenStatus1 = new Reading(val1, DeviceReadingTypes.STATE.name(), ReadingValues.OFF.name(), dateNow);
        Reading valOpenStatus2 = new Reading(val2, DeviceReadingTypes.STATE.name(), ReadingValues.OFF.name(), dateNow);
        Reading valOpenStatus3 = new Reading(val3, DeviceReadingTypes.STATE.name(), ReadingValues.ON.name(), dateNow);
        Reading valOpenStatus4 = new Reading(val4, DeviceReadingTypes.STATE.name(), ReadingValues.OFF.name(), dateNow);

        kieSession.insert(pump1);
        kieSession.insert(val1);
        kieSession.insert(val2);
        kieSession.insert(val3);
        kieSession.insert(val4);
        kieSession.insert(array1);
        kieSession.insert(array2);
        kieSession.insert(pumpStatus1);
        kieSession.insert(pumpState);
        kieSession.insert(valOpenStatus1);
        kieSession.insert(valOpenStatus2);
        kieSession.insert(valOpenStatus3);
        kieSession.insert(valOpenStatus4);

        kieSession.getAgenda().getAgendaGroup("irrigation").setFocus();
        kieSession.fireAllRules( );

    }
    
    @Test
    void testCloseValveWhenRaining() {
    	System.out.println("**************************");
        System.out.println("TEST Close valves when raining:");
        System.out.println("**************************");
    	initKie();
    	DeviceArray array1 = new DeviceArray();

        Device rainSensor = new Device("Rain1", DeviceDetails.RAIN_SENSOR, array1, 0);

        //Setting rain sensor readings
        Reading rain1 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "false", twoDaysAgo);
        Reading rain2 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "true", dateNow);
        Reading rain3 = new Reading(rainSensor, DeviceReadingTypes.RAIN.name(), "true", dateNow);
        rain1.setId(10l);
        rain2.setId(11l);
        rain3.setId(12l);

        kieSession.insert(rain1);
        kieSession.insert(rain2);
        kieSession.insert(rain3);
        kieSession.getAgenda().getAgendaGroup("irrigation").setFocus();
        kieSession.fireAllRules( );
    }
}
