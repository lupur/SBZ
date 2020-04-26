package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.DeviceArrayDto;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.repository.DeviceArrayRepository;
import com.sbz.agro.repository.FieldRepository;

@Service
public class DeviceArrayServiceImpl implements DeviceArrayService {

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    DeviceArrayRepository arrayRepository;

    @Override
    public boolean addArray(Long fieldId) {

        try {
            Field field = fieldRepository.findById(fieldId).get();
            DeviceArray dArray = new DeviceArray();
            dArray.setField(field);
            arrayRepository.save(dArray);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeArray(Long arrayId) {
        for (DeviceArray d : arrayRepository.findAll()) {
            if (d.getId() == arrayId) {
                arrayRepository.delete(d);
                return true;
            }
        }
        return false;
    }

    @Override
    public DeviceArrayDto getDeviceArray(Long arrayId) {

        for (DeviceArray da : arrayRepository.findAll()) {
            if (da.getId() == arrayId) {
                return new DeviceArrayDto(da);
            }
        }

        return null;
    }

    @Override
    public List<DeviceArrayDto> getFieldArrays(Long fieldId) {
        try {
            List<DeviceArray> devices = fieldRepository.findById(fieldId).get().getDeviceArrays();
            List<DeviceArrayDto> devicesDto = new ArrayList<>();

            for (DeviceArray da : devices) {
                DeviceArrayDto daDto = new DeviceArrayDto(da);
                devicesDto.add(daDto);
            }

            return devicesDto;
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
    public DeviceArrayDto getArrayOfField(Long fieldId, Long deviceId) {
        try {
            Field f = fieldRepository.findById(fieldId).get();
            for (DeviceArray da : f.getDeviceArrays()) {
                if (da.getId() == deviceId) {
                    return new DeviceArrayDto(da);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
