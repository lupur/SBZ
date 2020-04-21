package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.DiseaseDto;
import com.sbz.agro.model.Disease;
import com.sbz.agro.repository.DiseaseRepository;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    public boolean addDisease(String name) {
        // TODO Auto-generated method stub
        if (diseaseRepository.findDiseaseByName(name) != null)
            return false;

        Disease d = new Disease();
        d.setName(name);
        diseaseRepository.save(d);
        return true;
    }

    @Override
    public boolean removeDisease(Long id) {
        Disease d = getDisease(id);
        if (d == null) {
            return false;
        }
        diseaseRepository.delete(d);
        return true;
    }

    @Override
    public boolean updateDisease(DiseaseDto disease) {
        Disease d = getDisease(disease.getId());
        if (d == null) {
            return false;
        }

        d.setName(disease.getName());
        diseaseRepository.save(d);
        return true;
    }

    @Override
    public DiseaseDto getDiseaseDto(Long id) {
        Disease d = getDisease(id);
        if (d == null) {
            return null;
        }
        DiseaseDto dDto = new DiseaseDto();
        dDto.setId(d.getId());
        dDto.setName(d.getName());

        return dDto;
    }

    @Override
    public List<DiseaseDto> getAllDiseasesDto() {

        List<Disease> diseases = getAllDiseases();
        List<DiseaseDto> diseasesDto = new ArrayList<DiseaseDto>();

        if (diseases == null)
            return null;

        for (Disease d : diseases) {
            DiseaseDto dDto = new DiseaseDto();
            dDto.setId(d.getId());
            dDto.setName(d.getName());
            diseasesDto.add(dDto);
        }
        return diseasesDto;
    }

    private Disease getDisease(Long id) {
        try {
            Disease d = diseaseRepository.findById(id).get();
            return d;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private List<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

}
