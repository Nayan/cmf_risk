package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.conflux.dal.bo.Employee;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IEmployeeService;
import com.conflux.service.dto.EmployeeDTO;

@Service
public class EmployeeService extends AbstractBaseService implements
		IEmployeeService {

	@Override
	public List<EmployeeDTO> getAllEmployees(int officeId) {
		List<Employee> employees = getEmployeeDAO().find(officeId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		for (Employee employee : employees) {
			EmployeeDTO employeeDTO = getHelper().createEmployeeDTO(employee);
			employeeDTOs.add(employeeDTO);
		}
		return employeeDTOs;
	}

	@Override
	public EmployeeDTO getEmployeeById(int employeeId) {
		Employee employee = getEmployeeDAO().findById(employeeId);
		return getHelper().createCompleteEmployeeDTO(employee);
	}

}
