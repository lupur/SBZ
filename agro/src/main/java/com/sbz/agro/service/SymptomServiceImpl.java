package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.SymptomDto;
import com.sbz.agro.model.Symptom;
import com.sbz.agro.repository.SymptomRepository;

@Service
public class SymptomServiceImpl implements SymptomService {

    @Autowired
    SymptomRepository symptomRepository;

    @Override
    public boolean addSymptom(String name) {
        // TODO Auto-generated method stub
        if (symptomRepository.findSymptomByName(name) != null)
            return false;

        Symptom s = new Symptom();
        s.setName(name);
        symptomRepository.save(s);
        return true;
    }

    @Override
    public boolean removeSymptom(Long id) {
        Symptom s = getSymptom(id);
        if (s == null) {
            return false;
        }
        symptomRepository.delete(s);
        return true;
    }

    @Override
    public boolean updateSymptom(SymptomDto crop) {
        Symptom s = getSymptom(crop.getId());
        if (s == null) {
            return false;
        }

        s.setName(crop.getName());
        symptomRepository.save(s);
        return true;
    }

    @Override
    public SymptomDto getSymptomDto(Long id) {
        Symptom s = getSymptom(id);
        if (s == null) {
            return null;
        }
        SymptomDto sDto = new SymptomDto();
        sDto.setId(s.getId());
        sDto.setName(s.getName());

        return sDto;
    }

    @Override
    public List<SymptomDto> getAllSymptomsDto() {

        List<Symptom> diseases = getAllSymptoms();
        List<SymptomDto> cropsDto = new ArrayList<SymptomDto>();

        if (diseases == null)
            return null;

        for (Symptom s : diseases) {
            SymptomDto cDtop = new SymptomDto();
            cDtop.setId(s.getId());
            cDtop.setName(s.getName());
            cropsDto.add(cDtop);
        }
        return cropsDto;
    }

    private Symptom getSymptom(Long id) {
        try {
            Symptom s = symptomRepository.findById(id).get();
            return s;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

}
