package com.conflux.service.dto;



public class OccupationTypeDTO     {

	private int occupationTypeId;
	private String name;
	private String description;
	private boolean active;
	
	public int getOccupationTypeId() {
		return occupationTypeId;
	}
	public void setOccupationTypeId(int occupationTypeId) {
		this.occupationTypeId = occupationTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
} 
