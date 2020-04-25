package com.sbz.agro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.model.Field;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByOwner_Id(Long userId);
}
