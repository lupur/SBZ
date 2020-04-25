package com.sbz.agro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.model.*;
import com.sbz.agro.repository.FieldRepository;

@Service
public class DeviceArrayServiceImpl implements DeviceArrayService {
	
	@Autowired
	FieldRepository fieldRepository;

    @Override
    public boolean addArray(Long fieldId) {
    	Field field = fieldRepository.findById(fieldId).get();
    	
        if(field == null)
        	return false;
        
        DeviceArray dArray = new DeviceArray();
        dArray.setField(field);
        field.addDeviceArray(dArray);
        
        fieldRepository.save(field);
        
        return true;
    }

    @Override
    public boolean removeArray(Long arrayId) {
    	
    	for (Field f : fieldRepository.findAll()){
    		for (DeviceArray da : f.getDeviceArrays()){
    			if (da.getId() == arrayId){
    				f.getDeviceArrays().remove(da);
    				fieldRepository.save(f);
    				return true;
    			}
    		}
    	}
        
        return false;
    }

    @Override
    public DeviceArray getDeviceArray(Long arrayId) {
    	
    	for (Field f : fieldRepository.findAll()){
    		for (DeviceArray da : f.getDeviceArrays()){
    			if (da.getId() == arrayId){
    				return da;
    			}
    		}
    	}
    	
        return null;
    }

    @Override
    public List<DeviceArray> getFieldArrays(Long fieldId) {

        return fieldRepository.findById(fieldId).get().getDeviceArrays();
    }

    @Override
    public boolean arrayExists(Long arrayId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DeviceArray getArrayOfField(Long fieldId, Long deviceId) {
        // TODO Auto-generated method stub
        return null;
    }

}
