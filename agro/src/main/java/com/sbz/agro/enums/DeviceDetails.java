package com.sbz.agro.enums;

public enum DeviceDetails {
	
	// PUMP devices
	PUMP(1l, new DeviceReadingTypes[] {DeviceReadingTypes.STATUS, DeviceReadingTypes.STATE}),
	// SENSOR devices
	SENSOR(2l, new DeviceReadingTypes[] {DeviceReadingTypes.STATUS, DeviceReadingTypes.MOISTURE, DeviceReadingTypes.TEMPERATURE, DeviceReadingTypes.RAIN}),
	//VALVE devices
	VALVE(3l, new DeviceReadingTypes[] {DeviceReadingTypes.STATUS, DeviceReadingTypes.STATE});
	
	private Long typeId;
	private DeviceReadingTypes[] readingsTypes;
	
	DeviceDetails(Long typeId, DeviceReadingTypes[] readingsTypes) {
		this.typeId = typeId;
		this.readingsTypes = readingsTypes;
	}
	
	public Long getTypeId() {
		return typeId;
	}
	
	public DeviceReadingTypes[] getReadingTypes() {
		return readingsTypes;
	}
	
}
