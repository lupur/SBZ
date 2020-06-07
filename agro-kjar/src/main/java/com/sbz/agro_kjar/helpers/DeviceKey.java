package com.sbz.agro_kjar.helpers;

import java.util.HashMap;

public class DeviceKey {
    final Long arrayId;
    final Long deviceId;
    
    public DeviceKey(Long arrayId, Long deviceId)
    {
        this.arrayId = arrayId;
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        
        if(!(obj instanceof DeviceKey)) {
            return false;
        }

        DeviceKey deviceKey = (DeviceKey) obj;
        return deviceKey.arrayId == this.arrayId && deviceKey.deviceId == this.deviceId;
    }

    @Override
    public int hashCode() {
        return (int) (arrayId * 31 + deviceId);
    }

    @Override
    public String toString() {
        return "DeviceKey [arrayId=" + arrayId + ", deviceId=" + deviceId + "]";
    }
}