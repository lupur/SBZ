package com.sbz.agro;

import java.util.HashMap;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.repository.DeviceArrayRepository;
import com.sbz.agro.repository.DeviceRepository;
import com.sbz.agro.repository.FieldRepository;

@SpringBootApplication
public class AgroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgroApplication.class, args);

//        System.out.println("********************************************");
////        KieServices ks = KieServices.Factory.get();
////        KieContainer kc = ks.newKieContainer(ks.newReleaseId("com.sbz", "agro-kjar", "0.0.1-SNAPSHOT"));
////        KieSession kieSession = kc.newKieSession("irrigationSession");
//
//        List<String> supportedDevices = new ArrayList();
//        supportedDevices.add("PUMP");
//
//        KieSession kieSession = TemplateService.GenerateRules(supportedDevices);
//
//        Device d0 = new Device();
//        d0.setId(-1l);
//        d0.setSerialNo("PUMP_2212");
//
//        Device d1 = new Device();
//        d1.setId(-1l);
//        d1.setSerialNo("VALVE_2212");
//
//        kieSession.insert(d0);
//        kieSession.insert(d1);
//        kieSession.fireAllRules();
//
//        System.out.println("PUMP ID : " + d0.getId());
//        System.out.println("VALVE ID : " + d1.getId());
//
//        supportedDevices.add("VALVE");
//        kieSession = TemplateService.GenerateRules(supportedDevices);
//        d0 = new Device();
//        d0.setId(-1l);
//        d0.setSerialNo("PUMP_weqw");
//
//        d1 = new Device();
//        d1.setId(-1l);
//        d1.setSerialNo("VALVE_2212");
//
//        kieSession.insert(d0);
//        kieSession.insert(d1);
//        kieSession.fireAllRules();
//
//        System.out.println("PUMP ID : " + d0.getId());
//        System.out.println("VALVE ID : " + d1.getId());
    }

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceArrayRepository arrayRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz", "agro-kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10_000);
        return kContainer;
    }

    @Bean
    public KieSession kieIrrigationSession() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieContainer(ks.newReleaseId("com.sbz", "agro-kjar", "0.0.1-SNAPSHOT"));
        KieSession kieSession = kc.newKieSession("irrigationSession");

        new Thread() {
            @Override
            public void run() {
                kieSession.fireUntilHalt();
            }
        }.start();

//        KieServices ks = KieServices.Factory.get();
//        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz", "agro-kjar", "0.0.1-SNAPSHOT"));
//
//        KieSession kieSession = kContainer.newKieSession("irrigationSession");

        kieSession.setGlobal("valveStateMap", new HashMap<>());
        kieSession.setGlobal("pumpHasCapacity", true);

//        EntryPoint irrigationStream = kieSession.getEntryPoint("Irrigation");

        List<Device> devices = deviceRepository.findAll();
        for (Device d : devices) {
            kieSession.insert(d);
        }

        List<DeviceArray> arrays = arrayRepository.findAll();
        for (DeviceArray a : arrays) {
            kieSession.insert(a);
        }

        List<Field> fields = fieldRepository.findAll();
        for (Field f : fields) {
            kieSession.insert(f);
        }

//        kieSession.fireAllRules();
        return kieSession;
    }
}
