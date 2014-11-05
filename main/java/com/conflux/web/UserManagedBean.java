package com.conflux.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.UserT;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserManagedBean extends AbstractManagedBean implements
		Serializable {

	private static final long serialVersionUID = 1L;

	// Spring User Service is injected...

	List<UserT> userTs;

	private UserT userT = new UserT();

	public String navigateToNewUserPage() {
		userT = new UserT();
		return ApplicationConstants.USER_NAVIGATION.CREATE_USER.toString();
	}

	public String createNewUser() {
		// check if a product with this name exists
		if (getUserService().existsUserByUsername(userT.getUsername())) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"A user with this username alreay exists in the system",
									"A user with this username alreay exists in the system"));
			return null;
		}
		userT.setActive(true);
		userT = getUserService().addUser(userT);
		return ApplicationConstants.USER_NAVIGATION.VIEW_USER.toString();
	}

	public String viewUser(int userId) {
		userT = getUserService().getUserById(userId);
		return ApplicationConstants.USER_NAVIGATION.VIEW_USER.toString();
	}

	public String viewAllUsers() {
		userTs = getUserService().getUsers();
		return ApplicationConstants.USER_NAVIGATION.VIEW_USERS.toString();
	}

	public void updateLoggedInUser() {
		getUserService().updateUser(getLoginBean().getUserT());
	}

	public void onRowEdit(RowEditEvent event) {
		UserT userT = (UserT) event.getObject();
		getUserService().updateUser(userT);
	}

	public void delete(UserT userT) {
		if (userT.getId() == getLoginBean().getUserT().getId()) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"You cannot remove your own account from the system, please contact Chaitanya HO for further details",
									"You cannot remove your own account from the system, please contact Chaitanya HO for further details"));

			return;
		}
		userT.setActive(false);
		getUserService().updateUser(userT);
		// remove from user list
		userTs.remove(userT);
	}

	public UserT getUserT() {
		return userT;
	}

	public void setUserT(UserT userT) {
		this.userT = userT;
	}

	public List<UserT> getUserTs() {
		return userTs;
	}

	public void setUserTs(List<UserT> userTs) {
		this.userTs = userTs;
	}

}
