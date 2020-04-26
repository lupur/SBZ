package com.sbz.agro.dto;

import com.sbz.agro.model.Crop;

public class CropDto {
    private Long id;
    private String name;
    
    public CropDto(){
    	super();
    }
    
    public CropDto(Crop crop){
    	this.id = crop.getId();
    	this.name = crop.getName();
    }

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
}
