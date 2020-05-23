package com.sbz.agro.service;

import java.util.ArrayList;
import java.util.List;

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
    public List<ReadingDto> getReadingsBySerialNoAndName(String serialNo, String name) {

        Device device = deviceRepository.findBySerialNo(serialNo);
        if (device == null)
            return null;

        List<Reading> readings = readingRepository.findByDevice_IdAndName(device.getId(), name);
        List<ReadingDto> readingsDto = new ArrayList<>();
        for (Reading r : readings) {
            ReadingDto readingDto = new ReadingDto(r);
            readingsDto.add(readingDto);
        }
        // TODO Auto-generated method stub
        return readingsDto;
    }

}
