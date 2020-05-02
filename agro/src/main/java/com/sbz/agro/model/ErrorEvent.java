package com.sbz.agro.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kie.api.definition.type.PropertyReactive;
import org.kie.api.definition.type.Role;

@Entity
@Table(name = "error_event")
@Role(Role.Type.EVENT)
@PropertyReactive
public class ErrorEvent implements Serializable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "error_event_id")
	private Long id;
    @Column(name = "error_event_object_id")
	private Long objectId;
    @Column(name = "error_event_parent_id")
    private Long parentId;
    @Column(name = "error_event_type")
	private String type;
    @Column(name = "error_event_occured_at")
	private Date occuredAt;
    @Column(name = "error_event_fixed_at")
	private Date fixedAt;
	
	ErrorEvent() {
		
	}

	public ErrorEvent(Long objectId, Long parentId, String type, Date occuredAt) {
		this.objectId = objectId;
		this.parentId = parentId;
		this.type = type;
		this.occuredAt = occuredAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getOccuredAt() {
		return occuredAt;
	}

	public void setOccuredAt(Date occuredAt) {
		this.occuredAt = occuredAt;
	}

	public Date getFixedAt() {
		return fixedAt;
	}

	public void setFixedAt(Date fixedAt) {
		this.fixedAt = fixedAt;
	}
	
}
