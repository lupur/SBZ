package com.sbz.agro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "growth_phases")
public class GrowthPhase {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phase_id")
    private Long id;
	@Column(name = "phase_name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "crop_id", nullable = false)
	private Crop crop;
	@Column(name = "phase_start")	
	private int phaseStartDay;
	@Column(name = "phase_end")
	private int phaseEndDay;
	@Column(name = "moisture_max")
    private Double moistureUpperThreshold;
    @Column(name = "moisture_min")
    private Double moistureLowerThreshold;
	
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
	
	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}
	
	public int getPhaseStartDay() {
		return phaseStartDay;
	}
	
	public void setPhaseStartDay(int phaseStartDay) {
		this.phaseStartDay = phaseStartDay;
	}
	
	public int getPhaseEndDay() {
		return phaseEndDay;
	}
	
	public void setPhaseEndDay(int phaseEndDay) {
		this.phaseEndDay = phaseEndDay;
	}
	
	public Double getMoistureUpperThreshold() {
		return moistureUpperThreshold;
	}
	
	public void setMoistureUpperThreshold(Double moistureUpperThreshold) {
		this.moistureUpperThreshold = moistureUpperThreshold;
	}
	
	public Double getMoistureLowerThreshold() {
		return moistureLowerThreshold;
	}
	
	public void setMoistureLowerThreshold(Double moistureLowerThreshold) {
		this.moistureLowerThreshold = moistureLowerThreshold;
	}
}
