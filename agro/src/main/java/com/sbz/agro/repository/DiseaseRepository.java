package com.sbz.agro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.model.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Disease findDiseaseByName(String name);
}
