package com.conflux.service.dto;

import java.util.List;

public class AssetTypeDTO {

	private int assetTypeId;
	private String name;
	private String description;
	private List<AssetSubTypeDTO> assetSubTypeDTOs;

	public List<AssetSubTypeDTO> getAssetSubTypeDTOs() {
		return assetSubTypeDTOs;
	}

	public void setAssetSubTypeDTOs(List<AssetSubTypeDTO> assetSubTypeDTOs) {
		this.assetSubTypeDTOs = assetSubTypeDTOs;
	}

	public int getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
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

}
