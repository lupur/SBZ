package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.DeviceDto;
import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.enums.DeviceReadingTypes;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.repository.DeviceArrayRepository;
import com.sbz.agro.repository.DeviceRepository;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceArrayRepository arrayRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public List<DeviceDto> getArrayDevices(Long arrayId) {
        List<DeviceDto> devices = new ArrayList<DeviceDto>();
        List<Device> deviceDB = deviceRepository.findByArray_Id(arrayId);

        if (deviceDB == null)
            return null;

        for (Device devDB : deviceDB) {
            devices.add(new DeviceDto(devDB));
        }
        return devices;
    }

    @Override
    public DeviceDto getDeviceFromArray(Long arrayId, Long deviceId) {
        try {
            Device d = deviceRepository.findByIdAndArray_Id(deviceId, arrayId);
            return new DeviceDto(d);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deviceInArray(Long arrayId, Long deviceId) {
        try {
            Device d = deviceRepository.findByIdAndArray_Id(deviceId, arrayId);
            return d != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPositionAvailable(Long arrayId, Integer position, DeviceDetails type) {
        try {
            Device d = deviceRepository.findByArray_IdAndPositionAndType(arrayId, position, type);
            return d == null;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean addDevice(DeviceDto device) {
        try {
            DeviceArray array = arrayRepository.findById(device.getArrayId()).get();
            Device d = new Device();
            d.setArray(array);
            d.setPosition(device.getPosition());
            d.setSerialNo(device.getSerialNo());
            d.setType(device.getType());
            deviceRepository.save(d);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeDevice(Long deviceId) {
        try {
            Device d = deviceRepository.findById(deviceId).get();
            deviceRepository.delete(d);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean userOwnsDevice(Long deviceId, String username) {
        try {
            Device d = deviceRepository.findById(deviceId).get();
            if (!d.getArray().getField().getOwner().getUsername().equals(username)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean userOwnsDevice(String serialNo, String username) {
        try {
            Device d = deviceRepository.findBySerialNo(serialNo);
            if (!d.getArray().getField().getOwner().getUsername().equals(username)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isValidReadingName(String readingName) {
        for (DeviceReadingTypes s : DeviceReadingTypes.values()) {
            if (s.name().equals(readingName))
                return true;
        }
        return false;
    }

}
