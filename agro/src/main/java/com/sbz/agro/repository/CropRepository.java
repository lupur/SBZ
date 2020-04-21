package com.sbz.agro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.model.Crop;

public interface CropRepository extends JpaRepository<Crop, Long> {
    Crop findCropByName(String name);
}
