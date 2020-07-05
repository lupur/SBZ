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
import com.sbz.agro.repository.ErrorEventRepository;
import com.sbz.agro.repository.FieldRepository;
import com.sbz.agro.service.ErrorEventService;
import com.sbz.agro.service.ErrorEventServiceImpl;

@SpringBootApplication
public class AgroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgroApplication.class, args);
    }

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceArrayRepository arrayRepository;

    @Autowired
    FieldRepository fieldRepository;
    
    @Autowired
    ErrorEventService errorEventService;

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.sbz", "agro-kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10_000);
        return kContainer;
    }
    
    @Bean
    public KieSession kieErrorEventSession() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieContainer(ks.newReleaseId("com.sbz", "agro-kjar", "0.0.1-SNAPSHOT"));
        KieSession kieSession = kc.newKieSession("errorEventSession");
        
        new Thread() {
            @Override
            public void run() {
                kieSession.fireUntilHalt();
            }
        }.start();
        
        kieSession.setGlobal("errorEventService", new ErrorEventServiceImpl());
        
        return kieSession;
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
        kieSession.setGlobal("errorEventService", errorEventService);
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
