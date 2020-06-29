package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.CropDto;
import com.sbz.agro.model.Crop;
import com.sbz.agro.repository.CropRepository;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    CropRepository cropRepository;

    @Override
    public boolean addCrop(String name) {
        // TODO Auto-generated method stub
        if (cropRepository.findCropByName(name) != null)
            return false;

        Crop c = new Crop();
        c.setName(name);
        cropRepository.save(c);
        return true;
    }

    @Override
    public boolean removeCrop(Long id) {
        Crop c = getCrop(id);
        if (c == null) {
            return false;
        }
        
        try{
        	cropRepository.delete(c);
        } catch (Exception e){
        	return false;
        }
        return true;
    }

    @Override
    public boolean updateCrop(CropDto crop) {
        Crop c = getCrop(crop.getId());
        if (c == null) {
            return false;
        }

        c.setName(crop.getName());
        cropRepository.save(c);
        return true;
    }

    @Override
    public CropDto getCropDto(Long id) {
        Crop c = getCrop(id);
        if (c == null) {
            return null;
        }
        CropDto cDto = new CropDto();
        cDto.setId(c.getId());
        cDto.setName(c.getName());
        cDto.setGrowthPhases(c.getGrowthPhases());

        return cDto;
    }

    @Override
    public List<CropDto> getAllCropsDto() {

        List<Crop> crops = getAllCrops();
        List<CropDto> cropsDto = new ArrayList<CropDto>();

        if (crops == null)
            return null;

        for (Crop c : crops) {
            CropDto cDtop = new CropDto();
            cDtop.setId(c.getId());
            cDtop.setName(c.getName());
            cDtop.setGrowthPhases(c.getGrowthPhases());
            cropsDto.add(cDtop);
        }
        return cropsDto;
    }

    private Crop getCrop(Long id) {
        try {
            Crop c = cropRepository.findById(id).get();
            return c;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

}
