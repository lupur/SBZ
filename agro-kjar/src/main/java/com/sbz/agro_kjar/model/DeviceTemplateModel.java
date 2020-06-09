package com.sbz.agro_kjar.model;

public class DeviceTemplateModel {
    private String devicePrefix;

    public String getDevicePrefix() {
        return devicePrefix;
    }

    public void setDevicePrefix(String devicePrefix) {
        this.devicePrefix = devicePrefix;
    }

    public DeviceTemplateModel(String devicePrefix) {
        super();
        this.devicePrefix = devicePrefix;
    }

    public DeviceTemplateModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "DeviceTemplateModel [devicePrefix=" + devicePrefix + "]";
    }

    
}
