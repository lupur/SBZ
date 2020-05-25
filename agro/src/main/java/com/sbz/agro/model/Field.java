package com.sbz.agro.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Long id;
    @Column(name = "field_name")
    private String name;
    @Column(name = "field_area")
    private Double area;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;
    @OneToMany(mappedBy = "field", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    private Set<DeviceArray> deviceArrays;
    @Column(name = "seeding_date")
    private Date seedingDate;

    public Field() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DeviceArray> getDeviceArrays() {
        return deviceArrays;
    }

    public void setDeviceArrays(Set<DeviceArray> deviceArrays) {
        this.deviceArrays = deviceArrays;
    }

    public void addDeviceArray(DeviceArray deviceArray) {
        if (this.deviceArrays == null)
            this.deviceArrays = new HashSet<>();
        this.deviceArrays.add(deviceArray);
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Double getMoistureUpperThreshold() {
        for (GrowthPhase growthPhase : crop.getGrowthPhases()) {
            long diff = (new Date()).getTime() - seedingDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays >= growthPhase.getPhaseStartDay() && diffDays <= growthPhase.getPhaseEndDay()) {
                return growthPhase.getMoistureUpperThreshold();
            }
        }
        return -1D;
    }

    public Double getMoistureLowerThreshold() {
        System.out.println("Size: " + crop.getGrowthPhases().size());
        for (GrowthPhase growthPhase : crop.getGrowthPhases()) {
            long diff = (new Date()).getTime() - seedingDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays >= growthPhase.getPhaseStartDay() && diffDays <= growthPhase.getPhaseEndDay()) {
                return growthPhase.getMoistureLowerThreshold();
            }
        }
        return -1D;
    }

    public Date getSeedingDate() {
        return seedingDate;
    }

    public void setSeedingDate(Date seedingDate) {
        this.seedingDate = seedingDate;
    }

}
