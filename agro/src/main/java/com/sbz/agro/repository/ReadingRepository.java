package com.sbz.agro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sbz.agro.model.Reading;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    List<Reading> findByDevice_IdAndName(Long deviceId, String name);
    
    @Query(value = 
    		"SELECT * FROM sbz_db.device_readings " +
    		"WHERE device_reading_device = :deviceId " +
    		"AND device_reading_name = :name " +
    		"ORDER BY device_reading_ts " +
    		"DESC LIMIT 60",
    		nativeQuery = true)
    List<Reading> getLastReadings(Long deviceId, String name);
}
