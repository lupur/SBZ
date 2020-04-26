package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.DeviceDto;
import com.sbz.agro.enums.DeviceDetails;

public interface DeviceService {

    List<DeviceDto> getArrayDevices(Long arrayId);

    DeviceDto getDeviceFromArray(Long arrayId, Long deviceId);

    boolean deviceInArray(Long arrayId, Long deviceId);

    boolean isPositionAvailable(Long arrayId, Integer position, DeviceDetails type);

    boolean addDevice(DeviceDto device);

    boolean removeDevice(Long deviceId);
}
