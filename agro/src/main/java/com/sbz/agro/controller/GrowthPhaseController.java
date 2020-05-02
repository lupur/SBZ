package com.sbz.agro.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.GrowthPhaseDto;
import com.sbz.agro.security.TokenUtil;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.CropService;
import com.sbz.agro.service.GrowthPhaseService;

@RestController
@RequestMapping("crops/phases")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GrowthPhaseController {

	@Autowired
	AuthService authService;

	@Autowired
	GrowthPhaseService growthPhaseService;

	@Autowired
	CropService cropService;

	@Autowired
	TokenUtil tokenUtil;

	@PostMapping()
	public ResponseEntity addGrowthPhase(@RequestHeader("Token") String token,
	        @RequestBody GrowthPhaseDto growthPhase) {
		if (!authService.isAdmin(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (growthPhaseService.addGrowthPhase(growthPhase)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(value = "/{growthPhaseId}")
	public ResponseEntity deleteField(@RequestHeader("Token") String token, @PathVariable Long growthPhaseId) {
		if (!authService.isAdmin(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (growthPhaseService.removeGrowthPhase(growthPhaseId)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "{cropId}")
	public ResponseEntity getAllGrowthPhases(@RequestHeader("Token") String token, @PathVariable Long cropId) {
		if (!authService.atLeastUser(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Set<GrowthPhaseDto> gp = growthPhaseService.getAllCropGrowthPhases(cropId);

		return ResponseEntity.ok().body(gp);
	}
}