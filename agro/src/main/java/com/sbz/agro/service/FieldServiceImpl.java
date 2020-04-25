package com.sbz.agro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbz.agro.dto.FieldDto;

@Service
public class FieldServiceImpl implements FieldService {

    @Override
    public List<FieldDto> getAllFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<FieldDto> getUsersFields(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FieldDto getField(Long fieldId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean createField(FieldDto newField) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeField(Long fieldId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean userOwnsField(String username, Long fieldId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fieldExists(Long fieldId) {
        // TODO Auto-generated method stub
        return false;
    }

}
