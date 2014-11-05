package com.conflux.dal.bo;

// Generated 17 Jan, 2013 8:44:06 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Address generated by hbm2java
 */
@Entity
@Table(name = "address", catalog = "risk_module")
public class Address implements java.io.Serializable {

	private Integer addressId;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private Set<Employee> employeesForPermanentAddressId = new HashSet<Employee>(
			0);
	private Set<Employee> employeesForCurrentAddressId = new HashSet<Employee>(
			0);
	private Set<Customer> customers = new HashSet<Customer>(0);

	public Address() {
	}

	public Address(String line1, String line2, String city, String state,
			String zip, String country,
			Set<Employee> employeesForPermanentAddressId,
			Set<Employee> employeesForCurrentAddressId, Set<Customer> customers) {
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.employeesForPermanentAddressId = employeesForPermanentAddressId;
		this.employeesForCurrentAddressId = employeesForCurrentAddressId;
		this.customers = customers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "address_id", unique = true, nullable = false)
	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@Column(name = "line1", length = 500)
	public String getLine1() {
		return this.line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	@Column(name = "line2", length = 500)
	public String getLine2() {
		return this.line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	@Column(name = "city", length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zip", length = 45)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "country", length = 45)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addressByPermanentAddressId")
	public Set<Employee> getEmployeesForPermanentAddressId() {
		return this.employeesForPermanentAddressId;
	}

	public void setEmployeesForPermanentAddressId(
			Set<Employee> employeesForPermanentAddressId) {
		this.employeesForPermanentAddressId = employeesForPermanentAddressId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addressByCurrentAddressId")
	public Set<Employee> getEmployeesForCurrentAddressId() {
		return this.employeesForCurrentAddressId;
	}

	public void setEmployeesForCurrentAddressId(
			Set<Employee> employeesForCurrentAddressId) {
		this.employeesForCurrentAddressId = employeesForCurrentAddressId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
	public Set<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

}
