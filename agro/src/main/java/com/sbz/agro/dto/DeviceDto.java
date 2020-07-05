package com.sbz.agro.dto;

import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.model.Device;

public class DeviceDto {

	private Long id;
    private String serialNo;
    private Long arrayId;
    private DeviceDetails type;
    private Integer position;

    public DeviceDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DeviceDto(Device device) {
    	this.id = device.getId();
        this.serialNo = device.getSerialNo();
        this.arrayId = device.getArray().getId();
        this.type = device.getType();
        this.position = device.getPosition();
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getArrayId() {
        return arrayId;
    }

    public void setArrayId(Long arrayId) {
        this.arrayId = arrayId;
    }

    public DeviceDetails getType() {
        return type;
    }

    public void setType(DeviceDetails type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
