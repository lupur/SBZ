package com.sbz.agro.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sbz.agro.enums.DeviceDetails;

@Entity
@Table(name = "device_arrays")
public class DeviceArray {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_array_id")
    private Long id;
    @OneToMany(mappedBy = "array", fetch = FetchType.EAGER)
    private List<Device> devices;
    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    public DeviceArray() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Device> getValves() {
        List<Device> valves = new ArrayList<Device>();
        for(Device d : devices) {
            if(d.getType() == DeviceDetails.VALVE)
                valves.add(d);
        }
        return valves;
    }

    public List<Device> getMoistureSensors() {
        List<Device> moistureSensors = new ArrayList<Device>();
        for(Device d : devices) {
            if(d.getType() == DeviceDetails.MOISTURE_SENSOR)
            	moistureSensors.add(d);
        }
        return moistureSensors;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
