package com.sbz.agro.event;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("reportedAt")
@Expires("1h")
public class DeviceEvent implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private Date reportedAt;
    private Long deviceId;
    private Double value;

    public DeviceEvent() {
        super();
    }
    
    public DeviceEvent(Long deviceId, Double value) {
        super();
        this.reportedAt = new Date();
        this.deviceId = deviceId;
        this.value = value;
    }

	public DeviceEvent(DeviceEvent deviceEvent) {
		this.reportedAt = new Date();
		this.deviceId = deviceEvent.deviceId;
		this.value = deviceEvent.value;
	}

	public Date getReportedAt() {
		return reportedAt;
	}

	public void setReportedAt(Date reportedAt) {
		this.reportedAt = reportedAt;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
