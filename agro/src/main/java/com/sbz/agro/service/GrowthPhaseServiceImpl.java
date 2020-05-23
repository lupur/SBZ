package com.sbz.agro.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.GrowthPhaseDto;
import com.sbz.agro.model.Crop;
import com.sbz.agro.model.GrowthPhase;
import com.sbz.agro.repository.CropRepository;
import com.sbz.agro.repository.GrowthPhaseRepository;

@Service
public class GrowthPhaseServiceImpl implements GrowthPhaseService {

    @Autowired
    GrowthPhaseRepository growthPhaseRepository;

    @Autowired
    CropRepository cropRepository;

    @Override
    public Set<GrowthPhaseDto> getAllCropGrowthPhases(Long cropId) {
        try {
            Set<GrowthPhase> growthPhases = cropRepository.findById(cropId).get().getGrowthPhases();
            Set<GrowthPhaseDto> growthPhasesDto = new HashSet<>();

            for (GrowthPhase gp : growthPhases) {
                GrowthPhaseDto gpDto = new GrowthPhaseDto(gp);
                growthPhasesDto.add(gpDto);
            }

            return growthPhasesDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GrowthPhaseDto getGrowthPhase(Long growthPhaseId) {
        try {
            return new GrowthPhaseDto(growthPhaseRepository.findById(growthPhaseId).get());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean addGrowthPhase(GrowthPhaseDto newGrowthPhase) {
        if (newGrowthPhase == null)
            return false;

        try {
            Crop crop = cropRepository.findById(newGrowthPhase.getCropId()).get();
            GrowthPhase gp = new GrowthPhase();
            gp.setName(newGrowthPhase.getName());
            gp.setCrop(crop);
            gp.setPhaseStartDay(newGrowthPhase.getPhaseStartDay());
            gp.setPhaseEndDay(newGrowthPhase.getPhaseEndDay());
            gp.setMoistureUpperThreshold(newGrowthPhase.getMoistureUpperThreshold());
            gp.setMoistureLowerThreshold(newGrowthPhase.getMoistureLowerThreshold());
            growthPhaseRepository.save(gp);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeGrowthPhase(Long growthPhaseId) {
        try {
            GrowthPhase gp = growthPhaseRepository.findById(growthPhaseId).get();
            growthPhaseRepository.delete(gp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
