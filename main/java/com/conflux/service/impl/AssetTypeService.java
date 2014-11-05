package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.conflux.dal.bo.AssetSubType;
import com.conflux.dal.bo.AssetType;
import com.conflux.dal.bo.Office;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IAssetTypeService;
import com.conflux.service.dto.AssetSubTypeDTO;
import com.conflux.service.dto.AssetTypeDTO;

@Service
public class AssetTypeService extends AbstractBaseService implements
		IAssetTypeService {

	@Override
	public List<AssetSubTypeDTO> getAssetSubTypes(int officeId, int assetTypeId) {
		List<AssetSubType> assetSubTypes = getAssetSubTypeDAO().find(officeId,
				assetTypeId);
		List<AssetSubTypeDTO> assetSubTypeDTOs = new ArrayList<AssetSubTypeDTO>();
		for (AssetSubType assetSubType : assetSubTypes) {
			assetSubTypeDTOs.add(getHelper()
					.createAssetSubTypeDTO(assetSubType));
		}
		return assetSubTypeDTOs;
	}

	@Override
	public boolean existsAssetSubType(String name, int officeId, int assetTypeId) {
		AssetSubType assetSubType = getAssetSubTypeDAO().find(name, officeId,
				assetTypeId);
		if (assetSubType == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public AssetSubTypeDTO createAssetSubType(AssetSubTypeDTO assetSubTypeDTO,
			int officeId, int assetTypeId) {
		AssetSubType assetSubType = createAssetSubType(officeId, assetTypeId,
				assetSubTypeDTO);

		return getHelper().createAssetSubTypeDTO(assetSubType);
	}

	@Override
	public void createAssetSubTypes(List<AssetSubTypeDTO> assetSubTypeDTOs,
			int officeId, int assetTypeId) {
		for (AssetSubTypeDTO assetSubTypeDTO : assetSubTypeDTOs) {
			createAssetSubType(officeId, assetTypeId, assetSubTypeDTO);
		}

	}

	@Override
	public boolean deleteAssetSubType(int assetSubTypeId, String username) {
		AssetSubType assetSubType = getAssetSubTypeDAO().findById(
				assetSubTypeId);
		if (assetSubType != null) {
			assetSubType.setUpdatedBy(username);
			assetSubType.setUpdatedDate(new Date());
			getAssetSubTypeDAO().delete(assetSubType);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateAssetSubType(AssetSubTypeDTO assetSubTypeDTO) {
		AssetSubType assetSubType = getAssetSubTypeDAO().findById(
				assetSubTypeDTO.getAssetSubTypeId());
		if (assetSubType != null) {
			BeanUtils.copyProperties(assetSubTypeDTO, assetSubType);
			assetSubType.setUpdatedDate(new Date());
			getAssetSubTypeDAO().merge(assetSubType);
		}
	}

	/**
	 * @param officeId
	 * @param assetTypeId
	 * @param assetSubTypeDTO
	 * @return
	 */
	private AssetSubType createAssetSubType(int officeId, int assetTypeId,
			AssetSubTypeDTO assetSubTypeDTO) {
		AssetSubType assetSubType = getHelper().createAssetSubType(
				assetSubTypeDTO);
		Date todaysDate = new Date();
		assetSubType.setCreatedDate(todaysDate);
		assetSubType.setUpdatedDate(todaysDate);
		assetSubType.setActive(true);
		assetSubType.setAssetSubTypeId(null);
		// get the reference office and asset type
		Office office = getOfficeDAO().findById(officeId);
		AssetType assetType = getAssetTypeDAO().findById(assetTypeId);

		assetSubType.setOffice(office);
		assetSubType.setAssetType(assetType);

		getAssetSubTypeDAO().persist(assetSubType);

		// update references
		office.getAssetSubTypes().add(assetSubType);
		assetType.getAssetSubTypes().add(assetSubType);
		getOfficeDAO().merge(office);
		getAssetTypeDAO().merge(assetType);
		return assetSubType;
	}

	@Override
	public AssetSubTypeDTO getAssetSubTypeById(String id) {
		if (id != null && id.trim() != "") {
			int assetSubTypeId = Integer.parseInt(id);
			AssetSubType assetSubType = getAssetSubTypeDAO().findById(
					assetSubTypeId);
			return getHelper().createAssetSubTypeDTO(assetSubType);
		}
		return null;
	}

	@Override
	public AssetTypeDTO getAssetTypeById(int assetTypeId) {
		AssetType assetType = getAssetTypeDAO().findById(assetTypeId);
		return getHelper().createAssetTypeDTO(assetType);
	}

	
}
