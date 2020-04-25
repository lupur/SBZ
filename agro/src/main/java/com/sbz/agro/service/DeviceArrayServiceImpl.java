package com.sbz.agro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.repository.FieldRepository;

@Service
public class DeviceArrayServiceImpl implements DeviceArrayService {

    @Autowired
    FieldRepository fieldRepository;

    @Override
    public boolean addArray(Long fieldId) {

        try {
            Field field = fieldRepository.findById(fieldId).get();
            DeviceArray dArray = new DeviceArray();
            dArray.setField(field);
            field.addDeviceArray(dArray);
            fieldRepository.save(field);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeArray(Long arrayId) {

        for (Field f : fieldRepository.findAll()) {
            for (DeviceArray da : f.getDeviceArrays()) {
                if (da.getId() == arrayId) {
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

        for (Field f : fieldRepository.findAll()) {
            for (DeviceArray da : f.getDeviceArrays()) {
                if (da.getId() == arrayId) {
                    return da;
                }
            }
        }

        return null;
    }

    @Override
    public List<DeviceArray> getFieldArrays(Long fieldId) {
        try {
            return fieldRepository.findById(fieldId).get().getDeviceArrays();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean arrayExists(Long arrayId) {
        if (this.getDeviceArray(arrayId) == null)
            return false;

        return true;
    }

    @Override
    public DeviceArray getArrayOfField(Long fieldId, Long deviceId) {
        // TODO Auto-generated method stub
        return null;
    }
}
