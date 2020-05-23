package com.sbz.agro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.model.Reading;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    List<Reading> findByDevice_IdAndName(Long deviceId, String name);
}
