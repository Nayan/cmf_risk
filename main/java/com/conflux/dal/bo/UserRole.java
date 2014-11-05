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
 * UserRole generated by hbm2java
 */
@Entity
@Table(name = "user_role", catalog = "risk_module")
public class UserRole implements java.io.Serializable {

	private Integer userRoleId;
	private String roleName;
	private Set<PortalUser> portalUsers = new HashSet<PortalUser>(0);

	public UserRole() {
	}

	public UserRole(String roleName, Set<PortalUser> portalUsers) {
		this.roleName = roleName;
		this.portalUsers = portalUsers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_role_id", unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	@Column(name = "role_name", length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userRole")
	public Set<PortalUser> getPortalUsers() {
		return this.portalUsers;
	}

	public void setPortalUsers(Set<PortalUser> portalUsers) {
		this.portalUsers = portalUsers;
	}

}
