package com.sbz.agro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sbz.agro.enums.DeviceDetails;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;
    @Column(name = "device_serial_no")
    private String serialNo;
    @Column(name = "device_type_id", nullable = false)
    private DeviceDetails type;
    @ManyToOne
    @JoinColumn(name = "device_array_id", nullable = false)
    private DeviceArray array;
    @Column(name = "position")
    private Integer position;

    public Device() {

    }
    
	public Device(String serialNo, DeviceDetails type, DeviceArray array, Integer position) {
		this.serialNo = serialNo;
		this.type = type;
		this.array = array;
		this.position = position;
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

    public DeviceDetails getType() {
        return type;
    }

    public void setType(DeviceDetails type) {
        this.type = type;
    }

    public DeviceArray getArray() {
        return array;
    }

    public void setArray(DeviceArray array) {
        this.array = array;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
