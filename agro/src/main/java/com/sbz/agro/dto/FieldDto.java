package com.sbz.agro.dto;

import java.util.Date;

import com.sbz.agro.model.Field;

public class FieldDto {

    private Long id;
    private String name;
    private Double area;
    private Long ownerId;
    private Long cropId;
    private String ownerName;
    private String cropName;
    private Date seedingDate;

    public FieldDto() {
        super();
    }

    public FieldDto(Field field) {
        this.id = field.getId();
        this.name = field.getName();
        this.area = field.getArea();
        this.ownerName = field.getOwner().getUsername();
        this.cropName = field.getCrop().getName();
        this.seedingDate = field.getSeedingDate();
        this.ownerId = field.getOwner().getId();
        this.cropId = field.getCrop().getId();
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

    public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public Date getSeedingDate() {
        return seedingDate;
    }

    public void setSeedingDate(Date seedingDate) {
        this.seedingDate = seedingDate;
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
}
