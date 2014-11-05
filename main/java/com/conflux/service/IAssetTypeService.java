package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.AssetSubTypeDTO;
import com.conflux.service.dto.AssetTypeDTO;

public interface IAssetTypeService {

	@Transactional(readOnly = true)
	public List<AssetSubTypeDTO> getAssetSubTypes(int officeId, int assetTypeId);

	@Transactional(readOnly = true)
	public boolean existsAssetSubType(String name, int officeId, int assetTypeId);

	@Transactional
	public AssetSubTypeDTO createAssetSubType(AssetSubTypeDTO assetSubTypeDTO,
			int officeId, int assetTypeId);

	@Transactional
	public void createAssetSubTypes(List<AssetSubTypeDTO> assetSubTypeDTOs,
			int officeId, int assetTypeId);

	@Transactional
	public boolean deleteAssetSubType(int assetSubTypeId, String username);

	@Transactional
	public void updateAssetSubType(AssetSubTypeDTO assetSubTypeDTO);

	@Transactional
	public AssetSubTypeDTO getAssetSubTypeById(String id);
	
	@Transactional(readOnly = true)
	public AssetTypeDTO getAssetTypeById(int assetTypeId);
}
