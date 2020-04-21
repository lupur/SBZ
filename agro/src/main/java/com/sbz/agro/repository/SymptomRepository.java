package com.sbz.agro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.model.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    Symptom findSymptomByName(String name);
}
