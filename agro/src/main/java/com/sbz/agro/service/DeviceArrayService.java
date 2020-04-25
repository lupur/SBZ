package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.model.DeviceArray;

public interface DeviceArrayService {

    boolean addArray(Long fieldId);

    boolean removeArray(Long arrayId);

    DeviceArray getDeviceArray(Long arrayId);

    List<DeviceArray> getFieldArrays(Long fieldId);

    boolean arrayExists(Long arrayId);

    DeviceArray getArrayOfField(Long fieldId, Long arrayId);
}