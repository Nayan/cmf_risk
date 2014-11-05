package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.EmployeeDTO;

public interface IEmployeeService {

	@Transactional(readOnly = true)
	public List<EmployeeDTO> getAllEmployees(int officeId);

	@Transactional(readOnly = true)
	public EmployeeDTO getEmployeeById(int employeeId);

}
