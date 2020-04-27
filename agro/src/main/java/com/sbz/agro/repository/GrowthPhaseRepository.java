package com.sbz.agro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.model.GrowthPhase;

public interface GrowthPhaseRepository extends JpaRepository<GrowthPhase, Long> {
	GrowthPhase findGrowthPhaseByName(String name);
}