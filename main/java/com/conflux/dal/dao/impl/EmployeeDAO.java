package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.conflux.dal.bo.AuditType;
import com.conflux.dal.bo.Employee;
import com.conflux.dal.dao.IEmployeeDAO;

@Repository
public class EmployeeDAO extends BaseDAO implements IEmployeeDAO {

	@Override
	public void persist(Employee employee) {
		getSessionFactory().getCurrentSession().persist(employee);
	}

	@Override
	public void merge(Employee employee) {
		getSessionFactory().getCurrentSession().merge(employee);

	}

	@Override
	public void delete(Employee employee) {
		getSessionFactory().getCurrentSession().delete(employee);
	}

	@Override
	public Employee findById(int employeeId) {
		Employee employee = (Employee) getSessionFactory().getCurrentSession()
				.get("com.conflux.dal.bo.Employee", employeeId);
		return employee;
	}

	@Override
	public List<Employee> findAll() {
		List<Employee> employees = (List<Employee>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Employee").list();
		return employees;
	}

	@Override
	public List<Employee> find(int officeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.Employee employee where employee.office.officeId =:officeId order by employee.firstName");
		query.setParameter("officeId", officeId);
		return query.list();
	}
}
