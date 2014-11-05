package com.conflux.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.UserT;

@ManagedBean(name = "riskRatingBean")
@SessionScoped
public class RiskRatingManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	private String currentCenter;
	private String currentVillage;
	private int percentageCompletion = 0;
	//To identify auditor/junior auditor;
	private Map<String, ApplicationConstants.USER_ROLE> users; 

	public void calculateAllRiskRatings(){
		//Set user role details required for calculating Audit feedback risk factor for Center.
		loadUsers();
		Map<String, Integer> offices = getLoginBean().getOffices();
		//Get All Offices list
		List<Integer> officeIds = new ArrayList<Integer>(offices.values());
		for (Integer officeId : officeIds) {
			calculateOfficeRiskRating(officeId.intValue());
		}
		addFacesInfo("Rating Re calculation is completed.");
	}
	private void loadUsers() {
		if(null == users) users = new HashMap<String, ApplicationConstants.USER_ROLE>();
		// get a list of all users
		List<UserT> usersInDatabase = getUserService().getUsers();
		for (UserT userT : usersInDatabase) {
			if (userT.getUserRoleT().getRoleId() == ApplicationConstants.USER_ROLE.ADMIN
					.getRoleId()) {
				users.put(userT.getUsername(), ApplicationConstants.USER_ROLE.ADMIN);
			} else if (userT.getUserRoleT().getRoleId() == ApplicationConstants.USER_ROLE.AUDITOR
					.getRoleId()) {
				users.put(userT.getUsername(), ApplicationConstants.USER_ROLE.AUDITOR);
			} else if (userT.getUserRoleT().getRoleId() == ApplicationConstants.USER_ROLE.JUNIOR_AUDITOR
					.getRoleId()) {
				users.put(userT.getUsername(), ApplicationConstants.USER_ROLE.JUNIOR_AUDITOR);
			} else {
				users.put(userT.getUsername(), ApplicationConstants.USER_ROLE.REGULAR_USER);
			}
		}
	}
	public void calculateOfficeRiskRating(int officeId) {
		List<CustomerDTO> villages = getCustomerService().getVillages(officeId);
		for (CustomerDTO village : villages) {
			calculateVillageRiskRating(village);
		}
	}
	public void calculateVillageRiskRating(CustomerDTO village) {
		List<CustomerDTO> centers = getCustomerService().getCenters(village.getCustomerId());
		for (CustomerDTO center : centers) {
			calculateCenterRiskRating(center.getCustomerId());
		}				
		
		int villageRating = getRiskRatingService().calculateVillateRiskRating(centers);
		village.setRiskRating(Integer.toString(villageRating));
		updateRiskRating(village);
	}
	//Risk Rating calculation start
	public void calculateCenterRiskRating(int centerId) {
		if(null == users || users.isEmpty()) loadUsers();
		CustomerDTO center = getCustomerService().getCompleteCenterById(centerId);
		List<CustomerDTO> customers = center.getCustomerDTOs();
		// Calculate Customer Risk ratings
		for (CustomerDTO customer : customers) {
			calculateCustomerRiskRating(customer);
		}
		
		//Update center risk rating
		int centerRating = getRiskRatingService().calculateRiskRating(getRiskRatingService().getCenterRiskFactors(center, users));
		center.setRiskRating(Integer.toString(centerRating));

		updateRiskRating(center);
	}
	
	public void calculateCustomerRiskRating(int customerId) {
		CustomerDTO customer = getCustomerService().getCustomerById(customerId);
		calculateCustomerRiskRating(customer);
		addFacesInfo("Customer Rating Re calculation is completed.");
	}
	
	private void calculateCustomerRiskRating(CustomerDTO customer) {

		int rating = getRiskRatingService().calculateRiskRating(
				getRiskRatingService().getCustomerRiskFactors(customer));
		customer.setRiskRating(Integer.toString(rating));
		updateRiskRating(customer);
	}
	
	public void updateRiskRating(CustomerDTO customerDTO) {
		getCustomerService().updateNewRating(customerDTO.getCustomerId(),
				customerDTO.getRiskRating(),
				customerDTO.getRiskRatingChangeComment(),
				getLoginBean().getUserT().getUsername());
	}
	/**
	 * @return the currentCenter
	 */
	public String getCurrentCenter() {
		return currentCenter;
	}
	/**
	 * @param currentCenter the currentCenter to set
	 */
	public void setCurrentCenter(String currentCenter) {
		this.currentCenter = currentCenter;
	}
	/**
	 * @return the currentVillage
	 */
	public String getCurrentVillage() {
		return currentVillage;
	}
	/**
	 * @param currentVillage the currentVillage to set
	 */
	public void setCurrentVillage(String currentVillage) {
		this.currentVillage = currentVillage;
	}
	/**
	 * @return the percentageCompletion
	 */
	public int getPercentageCompletion() {
		return percentageCompletion;
	}
	/**
	 * @param percentageCompletion the percentageCompletion to set
	 */
	public void setPercentageCompletion(int percentageCompletion) {
		this.percentageCompletion = percentageCompletion;
	}
	
}
