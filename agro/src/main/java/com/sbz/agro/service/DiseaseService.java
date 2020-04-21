package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.DiseaseDto;

public interface DiseaseService {

    public boolean addDisease(String name);

    public boolean removeDisease(Long id);

    public boolean updateDisease(DiseaseDto crop);

    public DiseaseDto getDiseaseDto(Long id);

    public List<DiseaseDto> getAllDiseasesDto();
}
