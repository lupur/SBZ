package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.CropDto;

public interface CropService {

    public boolean addCrop(String name);

    public boolean removeCrop(Long id);

    public boolean updateCrop(CropDto crop);

    public CropDto getCropDto(Long id);

    public List<CropDto> getAllCropsDto();
}
