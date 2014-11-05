package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conflux.common.ApplicationBaseException;
import com.conflux.common.ApplicationConstants;
import com.conflux.dal.bo.PortalUser;
import com.conflux.dal.bo.UserRole;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IPortalUserService;
import com.conflux.service.dto.UserT;

@Service
public class PortalUserService extends AbstractBaseService implements
		IPortalUserService {

	@Transactional(rollbackFor = ApplicationBaseException.class)
	@Override
	public UserT addUser(UserT userT) {
		PortalUser portalUser = getHelper().createUser(userT);
		// associate a office with this user
		portalUser.setOffice(getOfficeDAO().findById(
				userT.getOfficeDTO().getOfficeId()));
		// ensure the Admin users can be created only in Head Office
		if (!(portalUser.getOffice().getOfficeType().getOfficeTypeId() == 1)) {
			UserRole userRole = getUserRoleDAO().findById(
					ApplicationConstants.USER_ROLE.REGULAR_USER.getRoleId());
			portalUser.setUserRole(userRole);
		}
		getPortalUserDAO().persist(portalUser);
		return getHelper().createUserDTO(portalUser);
	}

	@Transactional(rollbackFor = ApplicationBaseException.class)
	@Override
	public void deleteUser(UserT userT) {
		PortalUser portalUser = getHelper().createUser(userT);
		getPortalUserDAO().delete(portalUser);
	}

	@Transactional(rollbackFor = ApplicationBaseException.class)
	@Override
	public void updateUser(UserT userT) {
		PortalUser portalUser = getPortalUserDAO().findById(userT.getId());
		BeanUtils.copyProperties(userT, portalUser);
		getPortalUserDAO().merge(portalUser);
	}

	@Transactional(readOnly = true)
	@Override
	public UserT getUserById(int id) {
		return getHelper().createUserDTO(getPortalUserDAO().findById(id));
	}

	@Transactional(readOnly = true)
	@Override
	public UserT getUserByUsername(String username) {
		return getHelper().createUserDTO(
				getPortalUserDAO().findByUsername(username));
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserT> getUsers() {
		List<PortalUser> portalUsers = getPortalUserDAO().findAll();
		List<UserT> userTs = new ArrayList<UserT>();
		for (PortalUser portalUser : portalUsers) {
			userTs.add(getHelper().createUserDTO(portalUser));
		}
		return userTs;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existsUserByUsername(String username) {
		if (getPortalUserDAO().findByUsername(username) != null) {
			return true;
		} else {
			return false;
		}
	}
}
