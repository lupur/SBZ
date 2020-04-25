package com.sbz.agro.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="lands")
public class Field {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="land_id")
	private Long id;
	@Column(name="land_name")
	private String name;
	@Column(name="land_area")
	private Double area;
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User owner;
	@ManyToOne
    @JoinColumn(name="crop_id", nullable=false)
	private Crop crop;
	@OneToMany(mappedBy="land")
	private List<DeviceArray> deviceArrays;
	
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
	public List<DeviceArray> getDeviceArrays() {
		return deviceArrays;
	}
	public void setDeviceArrays(List<DeviceArray> deviceArrays) {
		this.deviceArrays = deviceArrays;
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
	
}
