package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.AssetDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.CustomerFamilyMemberDTO;
import com.conflux.service.dto.CustomerFamilyOccupationDTO;
import com.conflux.service.dto.ExternalLoanDTO;
import com.conflux.service.dto.OccupationDTO;

public interface ICustomerService {

	@Transactional(readOnly = true)
	public List<CustomerDTO> getVillages(int branchId);

	@Transactional(readOnly = true)
	public List<CustomerDTO> getCenters(int villageId);
	
	@Transactional(readOnly = true)
	public List<CustomerDTO> getCentersByBranchId(int villageId);

	@Transactional(readOnly = true)
	public List<CustomerDTO> getMembers(int centerId);

	@Transactional(readOnly = true)
	public CustomerDTO getCustomerById(int customerId);

	@Transactional(readOnly = true)
	public CustomerDTO getCenterById(int centerId);

	/**
	 * Returns complete Center along with Complete details of each client
	 * (Customer). This data is required for Risk ratings calculation
	 * 
	 * @param centerId
	 * @return
	 */
	@Transactional(readOnly = true)
	public CustomerDTO getCompleteCenterById(int centerId);

	@Transactional(readOnly = true)
	public OccupationDTO getCustomerOccupation(int customerId);

	@Transactional
	public OccupationDTO saveOrUpdateCustomerOccupation(CustomerDTO customer);

	@Transactional(readOnly = true)
	public List<AssetDTO> getCustomerAssets(int customerId);

	@Transactional
	public List<AssetDTO> saveOrUpdateCustomerAssets(int customerId,
			List<AssetDTO> assetDTOs, String username);

	@Transactional(readOnly = true)
	public List<CustomerFamilyMemberDTO> getCustomerFamilyDetails(int customerId);

	@Transactional
	public List<CustomerFamilyMemberDTO> saveOrUpdateCustomerFamilyDetails(
			CustomerDTO customerDTO,
			List<CustomerFamilyMemberDTO> customerFamilyMemberDTOs,
			String username);

	@Transactional(readOnly = true)
	public List<CustomerFamilyOccupationDTO> getCustomerFamilyOccupations(
			int customerId);

	@Transactional
	public List<CustomerFamilyOccupationDTO> saveOrUpdateCustomerFamilyOccupations(
			int customerId,
			List<CustomerFamilyOccupationDTO> customerFamilyOccupationDTOs,
			String username);

	@Transactional(readOnly = true)
	public List<ExternalLoanDTO> getCustomerExternalLoans(int customerId);

	@Transactional
	public List<ExternalLoanDTO> saveOrUpdateCustomerExternalLoans(
			int customerId, List<ExternalLoanDTO> externalLoanDTOs,
			String username);

	@Transactional(readOnly = true)
	public List<CustomerDTO> getCentersWithPendingUpdate(int branchId);
	
	@Transactional(readOnly = true)
	public List<CustomerDTO> getCustomersPendingUpdate(int centerId);

	@Transactional
	public void updateNewRating(int customerId, String newRating,
			String comment, String userName);

	@Transactional
	public void updateNewBMRating(int customerId, String newRating,
			String userName);
	
	@Transactional
	public void updateNewBMProblemDescription(int customerId, String problemDescription,
			String userName);
	
	@Transactional
	public void updateNewBMActionTaken(int customerId, String actionTaken,
			String userName);

	@Transactional
	public void updatePendingStatus(int customerId,	String userName);
	
	@Transactional
	public CustomerDTO getBasicCustomerInformationById(int customerId);

}
