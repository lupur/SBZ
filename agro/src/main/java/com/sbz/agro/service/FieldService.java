package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.FieldDto;
import com.sbz.agro.dto.FieldItemsDto;

public interface FieldService {

    List<FieldDto> getAllFields();

    List<FieldDto> getUsersFields(String username);

    FieldDto getField(Long fieldId);

    boolean createField(FieldDto newField);

    boolean removeField(Long fieldId);

    boolean userOwnsField(String username, Long fieldId);

    boolean fieldExists(Long fieldId);
    
    FieldItemsDto getFieldItems(Long fieldId);

}
