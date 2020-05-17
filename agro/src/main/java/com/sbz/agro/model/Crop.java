package com.sbz.agro.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "crops")
public class Crop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crop_id")
	private Long id;
	@Column(name = "crop_name")
	private String name;
	@OneToMany(mappedBy = "crop", fetch = FetchType.EAGER)
	private Set<GrowthPhase> growthPhases;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<GrowthPhase> getGrowthPhases() {
		return growthPhases;
	}

	public void setGrowthPhases(Set<GrowthPhase> growthPhases) {
		this.growthPhases = growthPhases;
	}
	
	public void addGrowthPhase(GrowthPhase growthPhase) {
		if(growthPhases == null) {
			growthPhases = new HashSet<>();
		}
		
		growthPhases.add(growthPhase);
	}
}
