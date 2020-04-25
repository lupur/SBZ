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
@Table(name="device_arrays")
public class DeviceArray {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="device_array_id")
	private Long id;
	@OneToMany(mappedBy="array")
	private List<Device> devices;
	@ManyToOne
    @JoinColumn(name="land_id", nullable=false)
	private Land land;
	
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

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

}
