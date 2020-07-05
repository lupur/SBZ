package com.sbz.agro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbz.agro.model.ErrorEvent;
import com.sbz.agro.repository.ErrorEventRepository;

@Service
public class ErrorEventServiceImpl implements ErrorEventService {
	
	@Autowired
	ErrorEventRepository errorEventRepository;

	@Override
	public ErrorEvent addOrUpdateErrorEvent(ErrorEvent errorEvent) {
		try {
			errorEvent = errorEventRepository.save(errorEvent);
			return errorEvent;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
