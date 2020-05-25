package com.sbz.agro.service;

import java.util.List;

import com.sbz.agro.dto.ReadingDto;
import com.sbz.agro.model.Reading;

public interface ReadingService {
    Reading addReading(ReadingDto newReading);

    List<ReadingDto> getReadingsBySerialNoAndName(String serialNo, String name);
}
