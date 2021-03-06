package com.sbz.agro.dto;

import java.util.ArrayList;
import java.util.List;

import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;

public class DeviceArrayDto {

    private Long id;
    private List<DeviceDto> devices = new ArrayList<>();
    private Long fieldId;

    public DeviceArrayDto(DeviceArray deviceArray) {

        if (deviceArray == null) {
            return;
        }

        this.id = deviceArray.getId();
        for(Device device : deviceArray.getDevices()) {
        	devices.add(new DeviceDto(device));
        }
        this.fieldId = deviceArray.getField().getId();
    }

    public DeviceArrayDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DeviceDto> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDto> devices) {
        this.devices = devices;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

}
