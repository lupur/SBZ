package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.FieldDto;

public interface FieldService {

    List<FieldDto> getAllFields();

    List<FieldDto> getUsersFields(String username);

    FieldDto getField(Long fieldId);

    boolean createField(FieldDto newField);

    boolean removeField(Long fieldId);
}
