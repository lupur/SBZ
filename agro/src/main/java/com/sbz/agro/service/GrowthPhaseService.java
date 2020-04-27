package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.GrowthPhaseDto;

public interface GrowthPhaseService {
	
	List<GrowthPhaseDto> getAllCropGrowthPhases(Long cropId);
	
	GrowthPhaseDto getGrowthPhase(Long growthPhaseId);
	
	boolean addGrowthPhase(GrowthPhaseDto newGrowthPhase);
	
	boolean removeGrowthPhase(Long growthPhaseId);
}
