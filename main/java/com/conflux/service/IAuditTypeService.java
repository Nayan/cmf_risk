package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.AuditTypeDTO;

public interface IAuditTypeService {

	@Transactional(readOnly = true)
	public boolean existsAuditTypeByName(String name, int entityTypeId);

	@Transactional
	public void createAuditType(AuditTypeDTO auditTypeDTO);

	@Transactional(readOnly = true)
	public List<AuditTypeDTO> getAllAuditTypes();

	@Transactional(readOnly = true)
	public List<AuditTypeDTO> getAllAuditTypes(int entityTypeId);

	@Transactional(readOnly = true)
	public AuditTypeDTO getAuditType(int auditTypeId);

	@Transactional
	public AuditTypeDTO updateAuditType(AuditTypeDTO auditTypeDTO,
			String username);
}
