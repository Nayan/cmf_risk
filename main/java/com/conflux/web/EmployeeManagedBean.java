package com.conflux.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.conflux.service.dto.EmployeeDTO;

@ManagedBean(name = "employeeBean")
@ViewScoped
public class EmployeeManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	@ManagedProperty(value = "#{appBean}")
	ApplicationManagedBean appBean;

	private EmployeeDTO employee;
	private List<EmployeeDTO> employees;
	private int branchId;

	public void getAllEmployees() {
		employees = getEmployeeService().getAllEmployees(branchId);
	}

	public void getEmployeeById(String employeeId) {
		employee = getEmployeeService().getEmployeeById(
				Integer.parseInt(employeeId));
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDTO> employees) {
		this.employees = employees;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

}
