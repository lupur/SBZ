package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.FieldDto;
import com.sbz.agro.model.Crop;
import com.sbz.agro.model.Field;
import com.sbz.agro.model.User;
import com.sbz.agro.repository.CropRepository;
import com.sbz.agro.repository.FieldRepository;
import com.sbz.agro.repository.UserRepository;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CropRepository cropRepository;

    @Override
    public List<FieldDto> getAllFields() {
        List<Field> fieldsDb = fieldRepository.findAll();
        List<FieldDto> fields = new ArrayList<FieldDto>();
        entityToDtoList(fieldsDb, fields);

        return fields;
    }

    @Override
    public List<FieldDto> getUsersFields(String username) {
        User u = userRepository.findByUsername(username);
        if (u == null)
            return null;

        List<Field> fieldsDb = fieldRepository.findByOwner_Id(u.getId());
        List<FieldDto> fields = new ArrayList<FieldDto>();

        entityToDtoList(fieldsDb, fields);

        return fields;
    }

    @Override
    public FieldDto getField(Long fieldId) {
        try {
            Field f = fieldRepository.findById(fieldId).get();
            FieldDto field = new FieldDto(f);
            return field;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public boolean createField(FieldDto newField) {
        if (newField == null)
            return false;

        try {
            User owner = userRepository.findById(newField.getOwnerId()).get();
            Crop crop = cropRepository.findById(newField.getCropId()).get();
            Field f = new Field();
            f.setArea(newField.getArea());
            f.setName(newField.getName());
            f.setOwner(owner);
            f.setCrop(crop);
            f.setSeedingDate(newField.getSeedingDate());
            fieldRepository.save(f);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeField(Long fieldId) {
        try {
            Field f = fieldRepository.findById(fieldId).get();
            fieldRepository.delete(f);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean userOwnsField(String username, Long fieldId) {
        User u = userRepository.findByUsername(username);
        if (u == null)
            return false;
        try {
            Field f = fieldRepository.findById(fieldId).get();
            return f.getOwner().getId() == u.getId();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean fieldExists(Long fieldId) {
        try {
            fieldRepository.findById(fieldId).get();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void entityToDtoList(List<Field> fieldsDb, List<FieldDto> fieldsDto) {
        if (fieldsDb == null || fieldsDto == null)
            return;

        for (Field f : fieldsDb) {
            FieldDto field = new FieldDto(f);

            fieldsDto.add(field);
        }

    }

}
