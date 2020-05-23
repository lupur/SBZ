package com.sbz.agro.service;

import java.util.Set;

import com.sbz.agro.dto.ReadingDto;
import com.sbz.agro.model.Reading;

public interface ReadingService {
    boolean addReading(ReadingDto newReading);

    Set<Reading> getReadingsBySerialNoAndName(String serialNo, String name);
}
