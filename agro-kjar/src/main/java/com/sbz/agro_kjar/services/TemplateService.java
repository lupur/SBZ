package com.sbz.agro_kjar.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import com.sbz.agro_kjar.model.DeviceTemplateModel;

public class TemplateService {

    public static KieSession GenerateRules(List<String> supportedDevices) {
        
        InputStream template = TemplateService.class.getResourceAsStream("/drools/template/device-supported.drt");

        List<DeviceTemplateModel> data = new ArrayList<DeviceTemplateModel>();
        
        for(String devicePrefix : supportedDevices) {
            data.add(new DeviceTemplateModel(devicePrefix));
        }
        
        ObjectDataCompiler converter =  new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        
        return createKieSessionFromDRL(drl);
    }
    
    private static KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        
        Results results = kieHelper.verify();
        
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }
            
            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        
        return kieHelper.build().newKieSession();
    }
}
