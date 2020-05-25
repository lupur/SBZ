package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.DeviceArrayDto;
import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.DeviceArray;
import com.sbz.agro.model.Field;
import com.sbz.agro.repository.DeviceArrayRepository;
import com.sbz.agro.repository.DeviceRepository;
import com.sbz.agro.repository.FieldRepository;

@Service
public class DeviceArrayServiceImpl implements DeviceArrayService {

    @Autowired
    FieldRepository fieldRepository;
    
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceArrayRepository arrayRepository;

    @Override
    public boolean addArray(Long fieldId) {

        try {
            Field field = fieldRepository.findById(fieldId).get();
            DeviceArray dArray = new DeviceArray();
            dArray.setField(field);
            arrayRepository.save(dArray);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
	@Override
	public boolean addArray(Long fieldId, String pumpEUI, String rainEUI) {
        try {
            Field field = fieldRepository.findById(fieldId).get();
            Device pump = deviceRepository.findBySerialNo(pumpEUI);
            Device rain = deviceRepository.findBySerialNo(rainEUI);
            if(pump != null || rain != null) throw new Exception("EUI already exists!");
            DeviceArray dArray = new DeviceArray();
            dArray.setField(field);
            dArray = arrayRepository.save(dArray);
            pump = new Device(pumpEUI, DeviceDetails.PUMP, dArray, 0);
            rain = new Device(rainEUI, DeviceDetails.RAIN_SENSOR, dArray, 0);
            deviceRepository.save(pump);
            deviceRepository.save(rain);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

    @Override
    public boolean removeArray(Long arrayId) {
        for (DeviceArray d : arrayRepository.findAll()) {
            if (d.getId() == arrayId) {
                arrayRepository.delete(d);
                return true;
            }
        }
        return false;
    }

    @Override
    public DeviceArrayDto getDeviceArray(Long arrayId) {

        for (DeviceArray da : arrayRepository.findAll()) {
            if (da.getId() == arrayId) {
                return new DeviceArrayDto(da);
            }
        }

        return null;
    }

    @Override
    public List<DeviceArrayDto> getFieldArrays(Long fieldId) {
        try {
            Set<DeviceArray> devices = fieldRepository.findById(fieldId).get().getDeviceArrays();
            List<DeviceArrayDto> devicesDto = new ArrayList<>();

            for (DeviceArray da : devices) {
                DeviceArrayDto daDto = new DeviceArrayDto(da);
                devicesDto.add(daDto);
            }

            return devicesDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean arrayExists(Long arrayId) {
        if (this.getDeviceArray(arrayId) == null)
            return false;

        return true;
    }

    @Override
    public DeviceArrayDto getArrayOfField(Long fieldId, Long deviceId) {
        try {
            Field f = fieldRepository.findById(fieldId).get();
            for (DeviceArray da : f.getDeviceArrays()) {
                if (da.getId() == deviceId) {
                    return new DeviceArrayDto(da);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

	@Override
	public boolean editArray(Long arrayId, String pumpEUI, String rainEUI) {
		try {
			Device pump = deviceRepository.findBySerialNo(pumpEUI);
			Device rain = deviceRepository.findBySerialNo(rainEUI);
			if(pump != null && !pump.getArray().getId().equals(arrayId) || rain != null && rain.getArray().getId().equals(arrayId)) throw new Exception("EUI already exists!");
			
			Set<Device> devices = deviceRepository.findByArray_Id(arrayId);
			for(Device device : devices) {
				if(device.getArray().getId().equals(arrayId)) {
					if(device.getType().name().equals(DeviceDetails.PUMP.name())) {
						device.setSerialNo(pumpEUI);
						deviceRepository.save(device);
					} else if(device.getType().name().equals(DeviceDetails.RAIN_SENSOR.name())) {
						device.setSerialNo(rainEUI);
						deviceRepository.save(device);
					}
				}
			}
		} catch(Exception e) {
			return false;
		}
		
		return  true;
	}

	@Override
	public boolean addSet(Long arrayId, String moistureEUI, String valveEUI) {
		try {
			Device moisture = deviceRepository.findBySerialNo(moistureEUI);
			Device valve = deviceRepository.findBySerialNo(valveEUI);
			if(moisture != null || valve != null) throw new Exception("EUIs already exist!");
			DeviceArray dArray = arrayRepository.getOne(arrayId);
			Set<Device> devices = deviceRepository.findByArray_Id(arrayId);
			Integer nextSetPosition = devices.size() / 2;
			if(devices.stream().anyMatch(d -> d.getPosition().equals(nextSetPosition))) throw new Exception("Array order in not correct!");
			
			
			moisture = new Device(moistureEUI, DeviceDetails.MOISTURE_SENSOR, dArray, nextSetPosition);
			valve = new Device(valveEUI, DeviceDetails.VALVE, dArray, nextSetPosition);
			
			deviceRepository.save(moisture);
			deviceRepository.save(valve);
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean editSet(Long arrayId, Integer position, String moistureEUI, String valveEUI) {
		try {
			Device moisture = deviceRepository.findBySerialNo(moistureEUI);
			Device valve = deviceRepository.findBySerialNo(valveEUI);
			if(moisture != null || valve != null) throw new Exception("EUIs already exist!");
			DeviceArray dArray = arrayRepository.getOne(arrayId);
			Set<Device> devices = deviceRepository.findByArray_Id(arrayId);
			for(Device device : devices) {
				if(device.getPosition().equals(position)) {
					if(device.getType().name().equals(DeviceDetails.MOISTURE_SENSOR.name())) {
						device.setSerialNo(moistureEUI);
						deviceRepository.save(device);
					} else if(device.getType().name().equals(DeviceDetails.VALVE.name())) {
						device.setSerialNo(valveEUI);
						deviceRepository.save(device);
					}
				}
			}

		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean removeSet(Long arrayId, Integer position) {
		try {
			DeviceArray dArray = arrayRepository.getOne(arrayId);
			Set<Device> devices = deviceRepository.findByArray_Id(arrayId);
			for(Device device : devices) {
				if(device.getPosition().equals(position)) {
					if(device.getType().name().equals(DeviceDetails.MOISTURE_SENSOR.name())) {
						deviceRepository.delete(device);
					} else if(device.getType().name().equals(DeviceDetails.VALVE.name())) {
						deviceRepository.delete(device);
					}
				}
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}

}
