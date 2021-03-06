package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.DeviceArrayDto;
import com.sbz.agro.dto.DeviceDto;
import com.sbz.agro.dto.FieldDto;
import com.sbz.agro.dto.FieldItemsDto;
import com.sbz.agro.dto.FieldItemsDto.ArrayDto;
import com.sbz.agro.dto.FieldItemsDto.ArrayDto.SetDto;
import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.enums.ErrorObjectTypes;
import com.sbz.agro.model.Crop;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.ErrorEvent;
import com.sbz.agro.model.Field;
import com.sbz.agro.model.User;
import com.sbz.agro.repository.CropRepository;
import com.sbz.agro.repository.DeviceArrayRepository;
import com.sbz.agro.repository.DeviceRepository;
import com.sbz.agro.repository.ErrorEventRepository;
import com.sbz.agro.repository.FieldRepository;
import com.sbz.agro.repository.UserRepository;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    FieldRepository fieldRepository;
    
    @Autowired
    DeviceArrayRepository deviceArrayRepository;
    
    @Autowired
    DeviceArrayService deviceArrayService;
    
    @Autowired
    DeviceRepository deviceRepository;
    
    @Autowired
    DeviceService deviceService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CropRepository cropRepository;
    
    @Autowired 
    ErrorEventRepository errorEventRepository;

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

	@Override
	public FieldItemsDto getFieldItems(Long fieldId) {
		
		FieldItemsDto fieldItemsDto = new FieldItemsDto();
		
		Field field = fieldRepository.findById(fieldId).get();
		fieldItemsDto.setName(field.getName());
		List<ErrorEvent> fieldErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(fieldId, ErrorObjectTypes.FIELD_ERROR.toString());
		if(fieldErrors != null && fieldErrors.size()>0) fieldItemsDto.setInErrorState(true);
		else fieldItemsDto.setInErrorState(false);
		List<DeviceArrayDto> deviceArrays = deviceArrayService.getFieldArrays(fieldId);
		for(DeviceArrayDto daDto : deviceArrays) { 
			com.sbz.agro.dto.FieldItemsDto.ArrayDto arrayDto = new com.sbz.agro.dto.FieldItemsDto.ArrayDto();
			arrayDto.setId(daDto.getId());
			List<ErrorEvent> arrayErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(daDto.getId(), ErrorObjectTypes.ARRAY_ERROR.toString());
			if(arrayErrors != null && arrayErrors.size()>0) arrayDto.setArrayInErrorState(true);
			else arrayDto.setArrayInErrorState(false);
			Set<DeviceDto> devices = deviceService.getArrayDevices(daDto.getId());
			for(DeviceDto device : devices) {
				if(device.getType().name().equals(DeviceDetails.PUMP.name())) {
					arrayDto.setPumpEUI(device.getSerialNo());
					List<ErrorEvent> pumpErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(device.getId(), ErrorObjectTypes.PUMP_ERROR.toString());
					if(pumpErrors != null && pumpErrors.size()>0) arrayDto.setPumpInErrorState(true);
					else arrayDto.setPumpInErrorState(false);
				} else if(device.getType().name().equals(DeviceDetails.RAIN_SENSOR.name())) {
					arrayDto.setRainEUI(device.getSerialNo());
				} else {
					Optional<SetDto> setDtoOptional = arrayDto.getSets().stream().filter(a -> a.getPosition().equals(device.getPosition())).findAny();
					SetDto setDto;
					if(!setDtoOptional.isPresent()) {
						setDto = new SetDto(null, null, device.getPosition());
						if(device.getType().name().equals(DeviceDetails.MOISTURE_SENSOR.name())) {
							setDto.setMoistureEUI(device.getSerialNo());
							List<ErrorEvent> moistureErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(device.getId(), ErrorObjectTypes.DEVICE_SET_ERROR.toString());
							if(moistureErrors != null && moistureErrors.size()>0) setDto.setMoistureInErrorState(true);
							else setDto.setMoistureInErrorState(false);
						}
						else if(device.getType().name().equals(DeviceDetails.VALVE.name())) {
							setDto.setValveEUI(device.getSerialNo());
							List<ErrorEvent> valveErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(device.getId(), ErrorObjectTypes.DEVICE_SET_ERROR.toString());
							if(valveErrors != null && valveErrors.size()>0) setDto.setValveInErrorState(true);
							else setDto.setValveInErrorState(false);
						}
						arrayDto.addSet(setDto);
					}
					else {
						if(device.getType().name().equals(DeviceDetails.MOISTURE_SENSOR.name())) {
							setDtoOptional.get().setMoistureEUI(device.getSerialNo());
							List<ErrorEvent> moistureErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(device.getId(), ErrorObjectTypes.DEVICE_SET_ERROR.toString());
							if(moistureErrors != null && moistureErrors.size()>0) setDtoOptional.get().setMoistureInErrorState(true);
							else setDtoOptional.get().setMoistureInErrorState(false);
						}
						else if(device.getType().name().equals(DeviceDetails.VALVE.name())) {
							setDtoOptional.get().setValveEUI(device.getSerialNo());
							List<ErrorEvent> valveErrors = errorEventRepository.getActiveErrorsByObjectIdAndType(device.getId(), ErrorObjectTypes.DEVICE_SET_ERROR.toString());
							if(valveErrors != null && valveErrors.size()>0) setDtoOptional.get().setValveInErrorState(true);
							else setDtoOptional.get().setValveInErrorState(false);				
						}
					}
				}
			}
			fieldItemsDto.addArray(arrayDto);
		}
		
		return fieldItemsDto;
	}

}
