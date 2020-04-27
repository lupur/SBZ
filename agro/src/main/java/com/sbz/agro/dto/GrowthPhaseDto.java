package com.sbz.agro.dto;

import com.sbz.agro.model.GrowthPhase;

public class GrowthPhaseDto {
	private Long id;
	private String name;
	private Long cropId;
	private int phaseStartDay;
	private int phaseEndDay;
	private Double moistureUpperThreshold;
    private Double moistureLowerThreshold;
	
    public GrowthPhaseDto(){
    	super();
    }
    
    public GrowthPhaseDto(GrowthPhase growthPhase){
    	this.id = growthPhase.getId();
    	this.name = growthPhase.getName();
    	this.cropId = growthPhase.getCrop().getId();
    	this.phaseStartDay = growthPhase.getPhaseStartDay();
    	this.phaseEndDay = growthPhase.getPhaseEndDay();
    	this.moistureUpperThreshold = growthPhase.getMoistureUpperThreshold();
    	this.moistureLowerThreshold = growthPhase.getMoistureLowerThreshold();
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

	public Long getCropId() {
		return cropId;
	}

	public void setCropId(Long cropId) {
		this.cropId = cropId;
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
