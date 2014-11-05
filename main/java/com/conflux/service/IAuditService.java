package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.AuditDTO;

public interface IAuditService {

	@Transactional
	public void save(AuditDTO audit, Boolean complete);

	@Transactional(readOnly = true)
	public List<AuditDTO> getAuditsInProgress(int officeId);

	@Transactional(readOnly = true)
	public AuditDTO getAudit(int auditId);

	@Transactional
	public void update(AuditDTO audit, Boolean complete);

}
