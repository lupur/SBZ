package com.sbz.agro.dto;

import java.util.List;

import com.sbz.agro.model.Crop;
import com.sbz.agro.model.GrowthPhase;

public class CropDto {
    private Long id;
    private String name;
    private List<GrowthPhase> growthPhases;
    
    public CropDto(){
    	super();
    }
    
    public CropDto(Crop crop){
    	this.id = crop.getId();
    	this.name = crop.getName();
    	this.growthPhases = crop.getGrowthPhases();
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

	public List<GrowthPhase> getGrowthPhases() {
		return growthPhases;
	}

	public void setGrowthPhases(List<GrowthPhase> growthPhases) {
		this.growthPhases = growthPhases;
	}
}
