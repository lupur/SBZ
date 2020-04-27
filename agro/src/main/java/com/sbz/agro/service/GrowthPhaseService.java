package com.sbz.agro.service;

import java.util.Set;

import com.sbz.agro.dto.GrowthPhaseDto;

public interface GrowthPhaseService {

	Set<GrowthPhaseDto> getAllCropGrowthPhases(Long cropId);

	GrowthPhaseDto getGrowthPhase(Long growthPhaseId);

	boolean addGrowthPhase(GrowthPhaseDto newGrowthPhase);

	boolean removeGrowthPhase(Long growthPhaseId);
}
