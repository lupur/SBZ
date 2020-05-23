package com.sbz.agro.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.dto.ReadingDto;
import com.sbz.agro.model.Device;
import com.sbz.agro.model.Reading;
import com.sbz.agro.repository.DeviceRepository;
import com.sbz.agro.repository.ReadingRepository;

@Service
public class ReadingServiceImpl implements ReadingService {

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public boolean addReading(ReadingDto newReading) {
        if (newReading == null)
            return false;

        try {
            Device device = deviceRepository.findBySerialNo(newReading.getSerialNo());
            Reading reading = new Reading();
            reading.setDevice(device);
            reading.setName(newReading.getName());
            reading.setTimestamp(newReading.getTimestamp());
            reading.setValue(newReading.getValue());
            readingRepository.save(reading);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Set<Reading> getReadingsBySerialNoAndName(String serialNo, String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
