package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.conflux.dal.bo.OccupationSubType;
import com.conflux.dal.bo.OccupationType;
import com.conflux.dal.bo.Office;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IOccupationTypeService;
import com.conflux.service.dto.OccupationSubTypeDTO;
import com.conflux.service.dto.OccupationTypeDTO;

@Service
public class OccupationTypeService extends AbstractBaseService implements
		IOccupationTypeService {

	@Override
	public List<OccupationSubTypeDTO> getOccupationSubTypes(int officeId,
			int occupationTypeId) {
		List<OccupationSubType> occupationSubTypes = getOccupationSubTypeDAO()
				.find(officeId, occupationTypeId);
		List<OccupationSubTypeDTO> occupationSubTypeDTOs = new ArrayList<OccupationSubTypeDTO>();
		for (OccupationSubType occupationSubType : occupationSubTypes) {
			occupationSubTypeDTOs.add(getHelper().createOccupationSubTypeDTO(
					occupationSubType));
		}
		return occupationSubTypeDTOs;
	}

	@Override
	public boolean existsOccupationSubType(String name, int officeId,
			int occupationTypeId) {
		OccupationSubType occupationSubType = getOccupationSubTypeDAO().find(
				name, officeId, occupationTypeId);
		if (occupationSubType == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public OccupationSubTypeDTO createOccupationSubType(
			OccupationSubTypeDTO occupationSubTypeDTO, int officeId,
			int occupationTypeId) {
		OccupationSubType occupationSubType = createOccupationSubType(officeId,
				occupationTypeId, occupationSubTypeDTO);

		return getHelper().createOccupationSubTypeDTO(occupationSubType);
	}

	@Override
	public void createOccupationSubTypes(
			List<OccupationSubTypeDTO> occupationSubTypeDTOs, int officeId,
			int occupationTypeId) {
		for (OccupationSubTypeDTO occupationSubTypeDTO : occupationSubTypeDTOs) {
			createOccupationSubType(officeId, occupationTypeId,
					occupationSubTypeDTO);
		}

	}

	@Override
	public boolean deleteOccupationSubType(int occupationSubTypeId,
			String username) {
		OccupationSubType occupationSubType = getOccupationSubTypeDAO()
				.findById(occupationSubTypeId);
		if (occupationSubType != null) {
			occupationSubType.setUpdatedBy(username);
			occupationSubType.setUpdatedDate(new Date());
			getOccupationSubTypeDAO().delete(occupationSubType);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateOccupationSubType(
			OccupationSubTypeDTO occupationSubTypeDTO) {
		OccupationSubType occupationSubType = getOccupationSubTypeDAO()
				.findById(occupationSubTypeDTO.getOccupationSubTypeId());
		if (occupationSubType != null) {
			BeanUtils.copyProperties(occupationSubTypeDTO, occupationSubType);
			occupationSubType.setUpdatedDate(new Date());
			getOccupationSubTypeDAO().merge(occupationSubType);
		}
	}

	/**
	 * @param officeId
	 * @param occupationTypeId
	 * @param occupationSubTypeDTO
	 * @return
	 */
	private OccupationSubType createOccupationSubType(int officeId,
			int occupationTypeId, OccupationSubTypeDTO occupationSubTypeDTO) {
		OccupationSubType occupationSubType = getHelper()
				.createOccupationSubType(occupationSubTypeDTO);
		Date todaysDate = new Date();
		occupationSubType.setCreatedDate(todaysDate);
		occupationSubType.setUpdatedDate(todaysDate);
		occupationSubType.setActive(true);
		occupationSubType.setOccupationSubTypeId(null);
		// get the reference office and occupation type
		Office office = getOfficeDAO().findById(officeId);
		OccupationType occupationType = getOccupationTypeDAO().findById(
				occupationTypeId);

		occupationSubType.setOffice(office);
		occupationSubType.setOccupationType(occupationType);

		getOccupationSubTypeDAO().persist(occupationSubType);

		// update references
		office.getOccupationSubTypes().add(occupationSubType);
		occupationType.getOccupationSubTypes().add(occupationSubType);
		getOfficeDAO().merge(office);
		getOccupationTypeDAO().merge(occupationType);
		return occupationSubType;
	}

	@Override
	public OccupationSubTypeDTO getOccupationSubTypeById(String id) {
		if (id != null && id.trim() != "") {
			int occupationSubTypeId = Integer.parseInt(id);
			OccupationSubType occupationSubType = getOccupationSubTypeDAO()
					.findById(occupationSubTypeId);
			return getHelper().createOccupationSubTypeDTO(occupationSubType);
		}
		return null;
	}

	@Override
	public OccupationTypeDTO getOccupationTypeById(int occupationTypeId) {
		OccupationType occupationType = getOccupationTypeDAO().findById(occupationTypeId);
		return getHelper().createOccupationTypeDTO(occupationType);
	}

}
