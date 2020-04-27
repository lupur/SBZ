package com.sbz.agro.dto;

import java.util.Date;

import com.sbz.agro.model.Field;

public class FieldDto {

    private Long id;
    private String name;
    private Double area;
    private Long ownerId;
    private Long cropId;
    private Date seedingDate;

    public FieldDto() {
        super();
    }

    public FieldDto(Field field) {
        this.id = field.getId();
        this.name = field.getName();
        this.area = field.getArea();
        this.ownerId = field.getOwner().getId();
        this.cropId = field.getCrop().getId();
        this.seedingDate = field.getSeedingDate();
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

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCropId() {
        return cropId;
    }

    public void setCropId(Long cropId) {
        this.cropId = cropId;
    }

    public Date getSeedingDate() {
        return seedingDate;
    }

    public void setSeedingDate(Date seedingDate) {
        this.seedingDate = seedingDate;
    }

}
