package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.model.DeviceArray;

public interface DeviceArrayService {

    boolean addArray(Long fieldId);

    boolean removeArray(Long fieldId);

    DeviceArray getDeviceArray(Long arrayId);

    List<DeviceArray> getFieldArrays(Long fieldId);
}