package com.sbz.agro.model;

import java.util.List;

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
    @Column(name="crop_id")
    private Long id;
    @Column(name="crop_name")
    private String name;
    @OneToMany(mappedBy = "crop", fetch = FetchType.EAGER)
    private List<GrowthPhase> growthPhases;

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

	public List<GrowthPhase> getGrowthPhases() {
		return growthPhases;
	}

	public void setGrowthPhases(List<GrowthPhase> growthPhases) {
		this.growthPhases = growthPhases;
	}
}
