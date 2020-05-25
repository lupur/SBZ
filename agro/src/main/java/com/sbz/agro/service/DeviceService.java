package com.sbz.agro.service;

import java.util.List;
import java.util.Set;

import com.sbz.agro.dto.DeviceDto;
import com.sbz.agro.enums.DeviceDetails;

public interface DeviceService {

    Set<DeviceDto> getArrayDevices(Long arrayId);

    DeviceDto getDeviceFromArray(Long arrayId, Long deviceId);

    boolean deviceInArray(Long arrayId, Long deviceId);

    boolean isPositionAvailable(Long arrayId, Integer position, DeviceDetails type);

    boolean addDevice(DeviceDto device);

    boolean removeDevice(Long deviceId);

    boolean userOwnsDevice(Long deviceId, String username);

    boolean userOwnsDevice(String serialNo, String username);

    boolean isValidReadingName(String readingName);
}
