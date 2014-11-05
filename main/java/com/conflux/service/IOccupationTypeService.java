package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.OccupationSubTypeDTO;
import com.conflux.service.dto.OccupationTypeDTO;

public interface IOccupationTypeService {

	@Transactional(readOnly = true)
	public List<OccupationSubTypeDTO> getOccupationSubTypes(int officeId,
			int occupationTypeId);

	@Transactional(readOnly = true)
	public boolean existsOccupationSubType(String name, int officeId,
			int occupationTypeId);

	@Transactional
	public OccupationSubTypeDTO createOccupationSubType(
			OccupationSubTypeDTO occupationSubTypeDTO, int officeId,
			int occupationTypeId);

	@Transactional
	public void createOccupationSubTypes(
			List<OccupationSubTypeDTO> occupationSubTypeDTOs, int officeId,
			int occupationTypeId);

	@Transactional
	public boolean deleteOccupationSubType(int occupationSubTypeId,
			String username);

	@Transactional
	public void updateOccupationSubType(
			OccupationSubTypeDTO occupationSubTypeDTO);

	@Transactional
	public OccupationSubTypeDTO getOccupationSubTypeById(String id);
	
	@Transactional (readOnly = true)
	public OccupationTypeDTO getOccupationTypeById(int occupationTypeId);
}
