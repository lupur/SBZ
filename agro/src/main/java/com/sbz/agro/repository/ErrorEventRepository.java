package com.sbz.agro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sbz.agro.model.ErrorEvent;

public interface ErrorEventRepository extends JpaRepository<ErrorEvent, Long> {
	
    @Query(value = 
    		"SELECT * FROM sbz_db.error_event " +
    		"WHERE error_event_object_id = :objectId " +
    		"AND error_event_type = :errorType " +
    		"AND error_event_fixed_at IS NULL",
    		nativeQuery = true)
	List<ErrorEvent> getActiveErrorsByObjectIdAndType(Long objectId, String errorType);

}