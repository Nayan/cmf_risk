package com.conflux.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.conflux.common.ApplicationConstants;
import com.conflux.common.IAuthenticationService;
import com.conflux.service.IBaseService;
import com.conflux.service.IPortalUserService;
import com.conflux.service.dto.OfficeDTO;
import com.conflux.service.dto.UserT;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private String currentPassword;
	private String newPassword;
	private UserT userT;
	private Map<String, Integer> offices = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> branchOffices = new LinkedHashMap<String, Integer>();

	@ManagedProperty(value = "#{authenticationService}")
	private IAuthenticationService authenticationService;

	@ManagedProperty(value = "#{portalUserService}")
	private IPortalUserService portalUserService;

	@ManagedProperty(value = "#{baseService}")
	private IBaseService baseService;

	public String login() {

		boolean success = authenticationService.login(login, password);

		if (success) { // set this users properties
			userT = portalUserService.getUserByUsername(login);
			// return users office
			if (!userT.getOfficeDTO().isBranchOffice()) {
				List<OfficeDTO> officesList = baseService.getAllOffices();
				for (OfficeDTO officeT : officesList) {
					offices.put(officeT.getName(), officeT.getOfficeId());
					if (officeT.isBranchOffice()) {
						branchOffices.put(officeT.getName(),
								officeT.getOfficeId());
					}
				}
			} else {
				offices = new LinkedHashMap<String, Integer>();
				offices.put(userT.getOfficeDTO().getName(), userT
						.getOfficeDTO().getOfficeId());
				branchOffices.put(userT.getOfficeDTO().getName(), userT
						.getOfficeDTO().getOfficeId());
			}
			return ApplicationConstants.SUCCESS;
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Incorrect username or Password",
							"Incorrect username or Password"));
			return ApplicationConstants.UNAUTHORIZED;
		}
	}

	public void updatePassword() {
		if (!currentPassword.equals(password)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Please check the password you have entered",
							"Please check the password you have entered"));
		} else {
			userT.setPassword(newPassword);
			password = newPassword;
			getPortalUserService().updateUser(userT);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sample info message",
							"Password changed successfully!"));
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserT getUserT() {
		return userT;
	}

	public void setUserT(UserT userT) {
		this.userT = userT;
	}

	public void setAuthenticationService(
			IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public Map<String, Integer> getOffices() {
		return offices;
	}

	public void setOffices(Map<String, Integer> offices) {
		this.offices = offices;
	}

	public IPortalUserService getPortalUserService() {
		return portalUserService;
	}

	public void setPortalUserService(IPortalUserService portalUserService) {
		this.portalUserService = portalUserService;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Map<String, Integer> getBranchOffices() {
		return branchOffices;
	}

	public void setBranchOffices(Map<String, Integer> branchOffices) {
		this.branchOffices = branchOffices;
	}

}
