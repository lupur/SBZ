package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.SymptomDto;

public interface SymptomService {

    public boolean addSymptom(String name);

    public boolean removeSymptom(Long id);

    public boolean updateSymptom(SymptomDto crop);

    public SymptomDto getSymptomDto(Long id);

    public List<SymptomDto> getAllSymptomsDto();
}
