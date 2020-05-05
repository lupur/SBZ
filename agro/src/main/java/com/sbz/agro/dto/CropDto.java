package com.sbz.agro.dto;

import java.util.HashSet;
import java.util.Set;

import com.sbz.agro.model.Crop;
import com.sbz.agro.model.GrowthPhase;

public class CropDto {
	private Long id;
	private String name;
	private Set<GrowthPhaseDto> growthPhases;

	public CropDto() {
		super();
	}

	public CropDto(Crop crop) {
		this.id = crop.getId();
		this.name = crop.getName();
		setGrowthPhases(crop.getGrowthPhases());
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

	public Set<GrowthPhaseDto> getGrowthPhases() {
		return growthPhases;
	}

	public void setGrowthPhases(Set<GrowthPhase> growthPhases) {
		if(this.growthPhases == null)
			this.growthPhases = new HashSet<>();
		for(GrowthPhase gP : growthPhases) {
			this.growthPhases.add(new GrowthPhaseDto(gP));
		}
	}
}
