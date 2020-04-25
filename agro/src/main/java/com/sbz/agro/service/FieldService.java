package com.sbz.agro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbz.agro.dto.FieldDto;

@Service
public interface FieldService {

    List<FieldDto> getAllFields();

    List<FieldDto> getUsersFields(String username);

    FieldDto getField(Long fieldId);

    boolean createField(FieldDto newField);

    boolean removeField(Long fieldId);

    boolean userOwnsField(String username, Long fieldId);

    boolean fieldExists(Long fieldId);

}
