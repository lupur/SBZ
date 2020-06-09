package com.sbz.agro.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Entity
@Table(name = "device_readings")
@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_reading_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_reading_device")
    private Device device;

    @Column(name = "device_reading_name")
    private String name;

    @Column(name = "device_reading_value")
    private String value;

    @Column(name = "device_reading_ts")
    private Date timestamp;

    public Reading() {
    }

    public Reading(Device device, String name, String value, Date timestamp) {
        this.device = device;
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Reading [id=" + id + ", device=" + device + ", name=" + name + ", value=" + value + ", timestamp="
                + timestamp + "]";
    }

}
