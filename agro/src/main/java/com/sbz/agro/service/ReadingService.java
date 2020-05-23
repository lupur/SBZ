package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.ReadingDto;

public interface ReadingService {
    boolean addReading(ReadingDto newReading);

    List<ReadingDto> getReadingsBySerialNoAndName(String serialNo, String name);
}
