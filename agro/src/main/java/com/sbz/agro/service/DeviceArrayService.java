package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.DeviceArrayDto;

public interface DeviceArrayService {

    boolean addArray(Long fieldId);

    boolean removeArray(Long arrayId);

    boolean arrayExists(Long arrayId);

    DeviceArrayDto getDeviceArray(Long arrayId);

    List<DeviceArrayDto> getFieldArrays(Long fieldId);

    DeviceArrayDto getArrayOfField(Long fieldId, Long arrayId);

	boolean addArray(Long fieldId, String pumpEUI, String rainEUI);

	boolean editArray(Long arrayId, String pumpEUI, String rainEUI);

	boolean addSet(Long arrayId, String pumpEUI, String rainEUI);
	
	boolean editSet(Long arrayId, Integer position, String pumpEUI, String rainEUI);
	
	boolean removeSet(Long arrayId, Integer position);

}