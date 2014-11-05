package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.conflux.dal.bo.Customer;
import com.conflux.dal.dao.ICustomerDAO;

@Repository
public class CustomerDAO extends BaseDAO implements ICustomerDAO {

	@Override
	public void persist(Customer customer) {
		getSessionFactory().getCurrentSession().persist(customer);
	}

	@Override
	public void merge(Customer customer) {
		getSessionFactory().getCurrentSession().merge(customer);

	}

	@Override
	public void delete(Customer customer) {
		getSessionFactory().getCurrentSession().delete(customer);
	}

	@Override
	public Customer findById(int customerId) {
		Customer customer = (Customer) getSessionFactory().getCurrentSession()
				.get("com.conflux.dal.bo.Customer", customerId);
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		List<Customer> customers = (List<Customer>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Customer").list();
		return customers;
	}

	@Override
	public List<Customer> find(int customerTypeId, int parentId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.Customer customer where customer.customerType.customerTypeId =:customerTypeId and customer.customer.customerId =:parentId order by customer.displayName");
		query.setParameter("customerTypeId", customerTypeId);
		query.setParameter("parentId", parentId);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.conflux.dal.dao.ICustomerDAO#findByPendingLoanUtilChecks(int)
	 * 
	 * refer http://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm
	 */
	@Override
	public List<Customer> findByPendingLoanUtilChecks(int officeId) {
		String sql = "select centre.* from customer centre,customer cust, loan_account "
				+ " where centre.customer_type_id=2 and cust.customer_type_id=4 and centre.customer_id=cust.parent_customer_id  and cust.customer_id = loan_account.customer_id and loan_account.pending_luc = 1 and centre.office_id =:officeId "
				+ " group by centre.customer_id";
		SQLQuery query = getSessionFactory().getCurrentSession()
				.createSQLQuery(sql);
		query.addEntity(Customer.class);
		query.setParameter("officeId", officeId);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.conflux.dal.dao.ICustomerDAO#findCentersWithClientsPendingUpdate(int)
	 */
	@Override
	public List<Customer> findCentersWithClientsPendingUpdate(int officeId) {
		String sql = "select centre.* from customer centre,customer cust "
				+ " where centre.customer_type_id=2 and cust.customer_type_id=4 and centre.customer_id=cust.parent_customer_id and cust.pending_update =true and centre.office_id =:officeId "
				+ " group by centre.customer_id";
		SQLQuery query = getSessionFactory().getCurrentSession()
				.createSQLQuery(sql);
		query.addEntity(Customer.class);
		query.setParameter("officeId", officeId);
		return query.list();
	}

	@Override
	public List<Customer> findClientsPendingUpdate(int centerId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.Customer customer where customer.customer.customerId =:centerId and customer.pendingUpdate =true and customer.customerType.customerTypeId =4 order by customer.firstName");
		query.setParameter("centerId", centerId);
		return query.list();
	}

	@Override
	public List<Customer> findAllVillages(int branchId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.Customer customer where customer.office.officeId =:officeId and customer.customerType.customerTypeId =1 order by customer.displayName");
		query.setParameter("officeId", branchId);
		return query.list();
	}

	/* (non-Javadoc)
	 * @see com.conflux.dal.dao.ICustomerDAO#findCentersByBranchId(int)
	 */
	@Override
	public List<Customer> findCentersByBranchId(int customerTypeId, int branchId) {
		Query query = getSessionFactory()
		.getCurrentSession()
		.createQuery(
				"from com.conflux.dal.bo.Customer customer where customer.customerType.customerTypeId =:customerTypeId and customer.office.officeId =:officeId order by customer.displayName");
		query.setParameter("customerTypeId", customerTypeId);
		query.setParameter("officeId", branchId);
		return query.list();
	}
	
}
