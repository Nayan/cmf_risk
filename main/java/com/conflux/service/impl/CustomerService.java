package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.conflux.common.ApplicationConstants.CUSTOMER_TYPE;
import com.conflux.dal.bo.Asset;
import com.conflux.dal.bo.AssetSubType;
import com.conflux.dal.bo.Customer;
import com.conflux.dal.bo.CustomerFamilyMember;
import com.conflux.dal.bo.CustomerFamilyOccupation;
import com.conflux.dal.bo.ExternalLoan;
import com.conflux.dal.bo.ExternalOrganization;
import com.conflux.dal.bo.IncomeLumping;
import com.conflux.dal.bo.Occupation;
import com.conflux.dal.bo.OccupationSubType;
import com.conflux.dal.bo.RelationshipType;
import com.conflux.dal.bo.RiskRatingHistory;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.ICustomerService;
import com.conflux.service.dto.AssetDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.CustomerFamilyMemberDTO;
import com.conflux.service.dto.CustomerFamilyOccupationDTO;
import com.conflux.service.dto.ExternalLoanDTO;
import com.conflux.service.dto.IncomeLumpingDTO;
import com.conflux.service.dto.OccupationDTO;

@Service
public class CustomerService extends AbstractBaseService implements
		ICustomerService {

	@Override
	public List<CustomerDTO> getVillages(int branchId) {
		List<Customer> customers = getCustomerDAO().findAllVillages(branchId);
		return populateCustomerDTOsList(customers);
	}

	@Override
	public List<CustomerDTO> getCenters(int villageId) {
		List<Customer> customers = getCustomerDAO().find(
				CUSTOMER_TYPE.CENTER.getCustomerTypeId(), villageId);
		return populateCustomerDTOsList(customers);
	}
	
	/* (non-Javadoc)
	 * @see com.conflux.service.ICustomerService#getCentersByBranchId(int)
	 */
	@Override
	public List<CustomerDTO> getCentersByBranchId(int branchId) {
		List<Customer> customers = getCustomerDAO().findCentersByBranchId(
				CUSTOMER_TYPE.CENTER.getCustomerTypeId(), branchId);
		return populateCustomerDTOsList(customers);
	}

	@Override
	public List<CustomerDTO> getMembers(int centerId) {
		List<Customer> customers = getCustomerDAO().find(
				CUSTOMER_TYPE.CLIENT.getCustomerTypeId(), centerId);
		return populateCustomerDTOsList(customers);
	}

	@Override
	public CustomerDTO getCustomerById(int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		return getHelper().createCompleteCustomerDTO(customer);
	}

	@Override
	public CustomerDTO getCenterById(int centerId) {
		Customer center = getCustomerDAO().findById(centerId);
		return getHelper().createCompleteCenterDTO(center);
	}
	
	/* (non-Javadoc)
	 * @see com.conflux.service.ICustomerService#getCompleteCenterById(int)
	 */
	@Override
	public CustomerDTO getCompleteCenterById(int centerId) {
		Customer center = getCustomerDAO().findById(centerId);
		return getHelper().createCompleteCenterDTOForRiskRating(center);
	}

	@Override
	public OccupationDTO getCustomerOccupation(int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		Set<Occupation> occupations = customer.getOccupations();
		if (occupations != null && occupations.size() > 0) {
			Occupation occupation = occupations.iterator().next();
			return getHelper().createCompleteOccupationDTO(occupation);
		} else {
			return null;
		}
	}

	@Override
	public OccupationDTO saveOrUpdateCustomerOccupation(CustomerDTO customerDto) {
		// Check if an occupation already exists
		Customer customer = getCustomerDAO().findById(
				customerDto.getCustomerId());
		Set<Occupation> occupations = customer.getOccupations();
		Occupation occupation = null;
		if (occupations != null && occupations.size() > 0) {
			occupation = occupations.iterator().next();
			updateCustomerOccupation(customerDto, occupation);
		} else if (customerDto.isEmployed()) {
			occupation = new Occupation();
			saveCustomerOccupation(customer, customerDto, occupation);
		}
		customer.setEmployed(customerDto.isEmployed());
		getCustomerDAO().merge(customer);
		if (customerDto.isEmployed()) {
			return getHelper().createCompleteOccupationDTO(occupation);
		} else {
			return new OccupationDTO();
		}

	}

	private void saveCustomerOccupation(Customer customer,
			CustomerDTO customerDto, Occupation occupation) {
		Date todaysDate = new Date();
		OccupationDTO occupationDTO = customerDto.getOccupationDTO();
		BeanUtils.copyProperties(occupationDTO, occupation);
		occupation.setOccupationId(null);
		occupation.setCreatedDate(todaysDate);
		occupation.setUpdatedDate(todaysDate);
		occupation.setCreatedBy(occupationDTO.getUpdatedBy());
		occupation.setCustomer(customer);

		// set the occupation subtype
		OccupationSubType occupationSubType = getOccupationSubTypeDAO()
				.findById(occupationDTO.getOccupationSubTypeId());
		occupation.setOccupationSubType(occupationSubType);
		// Save income lumping
		if (occupationSubType.getIncomeLumpingRequired() == true) {
			IncomeLumping incomeLumping = new IncomeLumping();
			IncomeLumpingDTO incomeLumpingDTO = occupationDTO
					.getIncomeLumpingDTO();
			saveIncomeLumping(incomeLumpingDTO, incomeLumping,
					occupationDTO.getUpdatedBy());
			// Set Income lumping
			occupation.setIncomeLumping(incomeLumping);
		}
		getOccupationDAO().persist(occupation);
		// merge dependencies
		customer.setUpdatedBy(occupation.getUpdatedBy());
		customer.setUpdatedDate(todaysDate);
		customer.getOccupations().add(occupation);
		getCustomerDAO().merge(customer);

		occupationSubType.getOccupations().add(occupation);
		getOccupationSubTypeDAO().merge(occupationSubType);
	}

	private void saveIncomeLumping(IncomeLumpingDTO incomeLumpingDTO,
			IncomeLumping incomeLumping, String updateBy) {
		Date todaysDate = new Date();
		BeanUtils.copyProperties(incomeLumpingDTO, incomeLumping);
		incomeLumping.setIncomeLumpingId(null);
		incomeLumping.setCreatedBy(updateBy);
		incomeLumping.setCreatedDate(todaysDate);
		incomeLumping.setModifiedBy(updateBy);
		incomeLumping.setModifiedDate(todaysDate);
		getIncomeLumpingDAO().persist(incomeLumping);
	}

	private void updateCustomerOccupation(CustomerDTO customerDto,
			Occupation occupation) {
		OccupationDTO occupationDTO = customerDto.getOccupationDTO();
		Date todaysDate = new Date();
		IncomeLumping incomeLumping = null;
		// If Employed, update occupation details
		if (customerDto.isEmployed()) {
			// merge existing occupation
			BeanUtils.copyProperties(occupationDTO, occupation);
			occupation.setUpdatedDate(todaysDate);
			// set the occupation subtype
			OccupationSubType occupationSubType = getOccupationSubTypeDAO()
					.findById(occupationDTO.getOccupationSubTypeId());
			if (occupationSubType.getMultiplierPresent() == false) {
				occupation.setMultiplierValue(0F);
			}
			occupation.setOccupationSubType(occupationSubType);
			incomeLumping = occupation.getIncomeLumping();
			// Save income lumping
			if (occupationSubType.getIncomeLumpingRequired() == true) {

				IncomeLumpingDTO incomeLumpingDTO = occupationDTO
						.getIncomeLumpingDTO();
				// If lumping is null create new Lumping
				if (incomeLumping == null) {
					incomeLumping = new IncomeLumping();
					saveIncomeLumping(incomeLumpingDTO, incomeLumping,
							occupationDTO.getUpdatedBy());
					occupation.setIncomeLumping(incomeLumping);
				} else {
					BeanUtils.copyProperties(incomeLumpingDTO, incomeLumping);
					incomeLumping.setModifiedDate(todaysDate);
					getIncomeLumpingDAO().merge(incomeLumping);
				}
			} else {
				if (null != incomeLumping
						&& null != incomeLumping.getIncomeLumpingId()
						&& incomeLumping.getIncomeLumpingId() != 0) {
					getIncomeLumpingDAO().delete(incomeLumping);
					occupation.setIncomeLumping(null);
				}
			}

			getOccupationDAO().merge(occupation);

		} else {// Delete occupation details
			getOccupationDAO().delete(occupation);
			incomeLumping = occupation.getIncomeLumping();
			if (null != incomeLumping
					&& null != incomeLumping.getIncomeLumpingId()
					&& incomeLumping.getIncomeLumpingId() != 0) {
				getIncomeLumpingDAO().delete(incomeLumping);
			}
		}
	}

	@Override
	public List<AssetDTO> getCustomerAssets(int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		Set<Asset> assets = customer.getAssets();
		return getHelper().createCompleteActiveAssetDTOs(assets);
	}

	@Override
	public List<AssetDTO> saveOrUpdateCustomerAssets(int customerId,
			List<AssetDTO> assetDTOs, String username) {

		// populate Customer family occupations map
		Map<Integer, AssetDTO> updatedAssetMap = new HashMap<Integer, AssetDTO>();
		// new family occupations to be added for this customer
		List<AssetDTO> newAssets = new ArrayList<AssetDTO>();
		// customer
		Customer customer = getCustomerDAO().findById(customerId);
		for (AssetDTO assetDTO : assetDTOs) {
			updatedAssetMap.put(assetDTO.getAssetId(), assetDTO);
			if (assetDTO.getAssetId() == 0) {
				newAssets.add(assetDTO);
			}
		}

		Date todaysDate = new Date();
		// update all existing and deleted loans
		for (Asset asset : customer.getAssets()) {
			if (updatedAssetMap.containsKey(asset.getAssetId())) {
				AssetDTO customerFamilyOccupationDTO = updatedAssetMap
						.get(asset.getAssetId());
				BeanUtils.copyProperties(customerFamilyOccupationDTO, asset);
				asset.setUpdatedBy(username);
				asset.setUpdatedDate(todaysDate);
				getAssetDAO().merge(asset);
			} else {
				// delete this loan
				asset.setActive(false);
				getAssetDAO().merge(asset);
			}
		}

		// add all new loans
		for (AssetDTO assetDTO : newAssets) {
			Asset asset = new Asset();
			BeanUtils.copyProperties(assetDTO, asset);
			asset.setAssetId(null);
			asset.setCreatedDate(todaysDate);
			asset.setUpdatedDate(todaysDate);
			asset.setCreatedBy(username);
			asset.setUpdatedBy(username);
			asset.setCustomer(customer);
			asset.setActive(true);
			AssetSubType assetSubType = getAssetSubTypeDAO().findById(
					assetDTO.getAssetSubTypeId());
			asset.setAssetSubType(assetSubType);
			getAssetDAO().persist(asset);
			customer.getAssets().add(asset);
		}

		getCustomerDAO().merge(customer);
		return getHelper().createCompleteActiveAssetDTOs(customer.getAssets());

	}

	@Override
	public List<CustomerFamilyMemberDTO> getCustomerFamilyDetails(int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		Set<CustomerFamilyMember> familyMembers = customer
				.getCustomerFamilyMembers();
		List<CustomerFamilyMemberDTO> customerFamilyMemberDTOs = new ArrayList<CustomerFamilyMemberDTO>();
		for (CustomerFamilyMember customerFamilyMember : familyMembers) {
			CustomerFamilyMemberDTO customerFamilyMemberDTO = getHelper()
					.createCustomerFamilyMemberDTO(customerFamilyMember);
			customerFamilyMemberDTOs.add(customerFamilyMemberDTO);
		}
		return customerFamilyMemberDTOs;
	}

	@Override
	public List<CustomerFamilyMemberDTO> saveOrUpdateCustomerFamilyDetails(
			CustomerDTO customerDTO,
			List<CustomerFamilyMemberDTO> customerFamilyMemberDTOs,
			String username) {

		// populate Customer family occupations map
		Map<Integer, CustomerFamilyMemberDTO> updatedCustomerFamilyMemberMap = new HashMap<Integer, CustomerFamilyMemberDTO>();
		// new family occupations to be added for this customer
		List<CustomerFamilyMemberDTO> newCustomerFamilyMembers = new ArrayList<CustomerFamilyMemberDTO>();
		// customer
		Customer customer = getCustomerDAO().findById(
				customerDTO.getCustomerId());
		for (CustomerFamilyMemberDTO customerFamilyMemberDTO : customerFamilyMemberDTOs) {
			updatedCustomerFamilyMemberMap.put(
					customerFamilyMemberDTO.getCustomerFamilyMemberId(),
					customerFamilyMemberDTO);
			if (customerFamilyMemberDTO.getCustomerFamilyMemberId() == 0) {
				newCustomerFamilyMembers.add(customerFamilyMemberDTO);
			}
		}

		Date todaysDate = new Date();
		// update all existing and deleted family members
		for (CustomerFamilyMember customerFamilyMember : customer
				.getCustomerFamilyMembers()) {
			if (updatedCustomerFamilyMemberMap.containsKey(customerFamilyMember
					.getCustomerFamilyMemberId())) {
				CustomerFamilyMemberDTO customerFamilyOccupationDTO = updatedCustomerFamilyMemberMap
						.get(customerFamilyMember.getCustomerFamilyMemberId());
				BeanUtils.copyProperties(customerFamilyOccupationDTO,
						customerFamilyMember);
				customerFamilyMember.setUpdatedBy(username);
				customerFamilyMember.setUpdatedDate(todaysDate);
				getCustomerFamilyMemberDAO().merge(customerFamilyMember);
			} else {
				// delete this loan
				customerFamilyMember.setActive(false);
				getCustomerFamilyMemberDAO().merge(customerFamilyMember);
			}
		}

		// add all new Family member details
		for (CustomerFamilyMemberDTO customerFamilyMemberDTO : newCustomerFamilyMembers) {
			CustomerFamilyMember customerFamilyMember = new CustomerFamilyMember();
			BeanUtils.copyProperties(customerFamilyMemberDTO,
					customerFamilyMember);
			customerFamilyMember.setCustomerFamilyMemberId(null);
			customerFamilyMember.setCreatedDate(todaysDate);
			customerFamilyMember.setUpdatedDate(todaysDate);
			customerFamilyMember.setCreatedBy(username);
			customerFamilyMember.setUpdatedBy(username);
			customerFamilyMember.setCustomer(customer);
			customerFamilyMember.setActive(true);
			// populate relationship type
			RelationshipType relationshipType = getRelationshipTypeDAO()
					.findById(customerFamilyMemberDTO.getRelationshipId());
			customerFamilyMember.setRelationshipType(relationshipType);
			getCustomerFamilyMemberDAO().persist(customerFamilyMember);
			getRelationshipTypeDAO().merge(relationshipType);
			customer.getCustomerFamilyMembers().add(customerFamilyMember);
		}

		// add Number of Family members, Number of minor dependents, Number of
		// Senior dependents.
		customer.setNumFamilyMembers(customerDTO.getNumFamilyMembers());
		customer.setNumMinDependents(customerDTO.getNumMinDependents());
		customer.setNumSeniorDependents(customerDTO.getNumSeniorDependents());
		getCustomerDAO().merge(customer);
		return getHelper().createCompleteActiveCustomerFamilyMemberDTOs(
				customer.getCustomerFamilyMembers());

	}

	@Override
	public List<CustomerFamilyOccupationDTO> getCustomerFamilyOccupations(
			int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		return getHelper().createCompleteActiveCustomerFamilyOccupationDTOs(
				customer.getCustomerFamilyOccupations());
	}

	@Override
	public List<CustomerFamilyOccupationDTO> saveOrUpdateCustomerFamilyOccupations(
			int customerId,
			List<CustomerFamilyOccupationDTO> customerFamilyOccupationDTOs,
			String username) {

		// populate Customer family occupations map
		Map<Integer, CustomerFamilyOccupationDTO> updatedCustomerFamilyOccupationMap = new HashMap<Integer, CustomerFamilyOccupationDTO>();
		// new family occupations to be added for this customer
		List<CustomerFamilyOccupationDTO> newCustomerFamilyOccupations = new ArrayList<CustomerFamilyOccupationDTO>();
		// customer
		Customer customer = getCustomerDAO().findById(customerId);
		for (CustomerFamilyOccupationDTO customerFamilyOccupationDTO : customerFamilyOccupationDTOs) {
			updatedCustomerFamilyOccupationMap
					.put(customerFamilyOccupationDTO
							.getCustomerFamilyOccupationId(),
							customerFamilyOccupationDTO);
			if (customerFamilyOccupationDTO.getCustomerFamilyOccupationId() == 0) {
				newCustomerFamilyOccupations.add(customerFamilyOccupationDTO);
			}
		}

		updateCustomerFamilyOccupations(customer,
				updatedCustomerFamilyOccupationMap, username);
		saveCustomerFamilyOccupations(customer, newCustomerFamilyOccupations,
				username);

		getCustomerDAO().merge(customer);
		return getHelper().createCompleteActiveCustomerFamilyOccupationDTOs(
				customer.getCustomerFamilyOccupations());

	}

	private void saveCustomerFamilyOccupations(Customer customer,
			List<CustomerFamilyOccupationDTO> newCustomerFamilyOccupations,
			String username) {
		// add all new family occupations
		Date todaysDate = new Date();
		IncomeLumping incomeLumping = null;
		IncomeLumpingDTO incomeLumpingDTO = null;
		for (CustomerFamilyOccupationDTO customerFamilyOccupationDTO : newCustomerFamilyOccupations) {
			CustomerFamilyOccupation customerFamilyOccupation = new CustomerFamilyOccupation();
			BeanUtils.copyProperties(customerFamilyOccupationDTO,
					customerFamilyOccupation);
			customerFamilyOccupation.setCustomerFamilyOccupationId(null);
			customerFamilyOccupation.setCreatedDate(todaysDate);
			customerFamilyOccupation.setUpdatedDate(todaysDate);
			customerFamilyOccupation.setCreatedBy(username);
			customerFamilyOccupation.setUpdatedBy(username);
			customerFamilyOccupation.setCustomer(customer);
			customerFamilyOccupation.setActive(true);
			OccupationSubType occupationSubType = getOccupationSubTypeDAO()
					.findById(
							customerFamilyOccupationDTO
									.getOccupationSubTypeId());
			customerFamilyOccupation.setOccupationSubType(occupationSubType);
			customerFamilyOccupation.setMultiplierPresent(occupationSubType
					.getMultiplierPresent());

			// Save income lumping
			if (occupationSubType.getIncomeLumpingRequired()) {
				incomeLumpingDTO = customerFamilyOccupationDTO
						.getIncomeLumpingDTO();
				incomeLumping = new IncomeLumping();
				BeanUtils.copyProperties(incomeLumpingDTO, incomeLumping);
				incomeLumping.setIncomeLumpingId(null);
				incomeLumping.setCreatedBy(customerFamilyOccupationDTO
						.getUpdatedBy());
				incomeLumping.setCreatedDate(todaysDate);
				incomeLumping.setModifiedBy(customerFamilyOccupationDTO
						.getUpdatedBy());
				incomeLumping.setModifiedDate(todaysDate);
				getIncomeLumpingDAO().persist(incomeLumping);
				// Set Income lumping
				customerFamilyOccupation.setIncomeLumping(incomeLumping);
			}
			getCustomerFamilyOccupationDAO().persist(customerFamilyOccupation);

			customer.getCustomerFamilyOccupations().add(
					customerFamilyOccupation);
		}

	}

	private void updateCustomerFamilyOccupations(
			Customer customer,
			Map<Integer, CustomerFamilyOccupationDTO> updatedCustomerFamilyOccupationMap,
			String username) {
		Date todaysDate = new Date();
		IncomeLumping incomeLumping = null;
		IncomeLumpingDTO incomeLumpingDTO = null;
		// update all existing and deleted loans
		for (CustomerFamilyOccupation customerfamilyOccupation : customer
				.getCustomerFamilyOccupations()) {
			incomeLumping = customerfamilyOccupation.getIncomeLumping();
			if (updatedCustomerFamilyOccupationMap
					.containsKey(customerfamilyOccupation
							.getCustomerFamilyOccupationId())) {
				CustomerFamilyOccupationDTO customerFamilyOccupationDTO = updatedCustomerFamilyOccupationMap
						.get(customerfamilyOccupation
								.getCustomerFamilyOccupationId());
				BeanUtils.copyProperties(customerFamilyOccupationDTO,
						customerfamilyOccupation);
				customerfamilyOccupation.setUpdatedBy(username);
				customerfamilyOccupation.setUpdatedDate(todaysDate);
				getCustomerFamilyOccupationDAO()
						.merge(customerfamilyOccupation);
				OccupationSubType occupationSubType = customerfamilyOccupation
						.getOccupationSubType();
				if (occupationSubType.getIncomeLumpingRequired()) {
					incomeLumpingDTO = customerFamilyOccupationDTO
							.getIncomeLumpingDTO();
					incomeLumping.setModifiedDate(todaysDate);
					BeanUtils.copyProperties(incomeLumpingDTO, incomeLumping);
					getIncomeLumpingDAO().merge(incomeLumping);
				} else {
					if (null != incomeLumping
							&& null != incomeLumping.getIncomeLumpingId()
							&& incomeLumping.getIncomeLumpingId() != 0) {
						getIncomeLumpingDAO().delete(incomeLumping);
					}
				}
			} else {
				// delete this loan
				customerfamilyOccupation.setIncomeLumping(null);
				customerfamilyOccupation.setActive(false);
				getCustomerFamilyOccupationDAO()
						.merge(customerfamilyOccupation);
				if (null != incomeLumping
						&& null != incomeLumping.getIncomeLumpingId()
						&& incomeLumping.getIncomeLumpingId() != 0) {
					getIncomeLumpingDAO().delete(incomeLumping);
				}
			}
		}
	}

	@Override
	public List<ExternalLoanDTO> getCustomerExternalLoans(int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		return getHelper().createCompleteActiveExternalLoanDTOs(
				customer.getExternalLoans());
	}

	@Override
	public List<ExternalLoanDTO> saveOrUpdateCustomerExternalLoans(
			int customerId, List<ExternalLoanDTO> externalLoanDTOs,
			String username) {
		// populate external Loans map
		Map<Integer, ExternalLoanDTO> updatedExternalLoansMap = new HashMap<Integer, ExternalLoanDTO>();
		// new external loans to be added for this customer
		List<ExternalLoanDTO> newExternalLoans = new ArrayList<ExternalLoanDTO>();
		// customer
		Customer customer = getCustomerDAO().findById(customerId);
		for (ExternalLoanDTO externalLoanDTO : externalLoanDTOs) {
			updatedExternalLoansMap.put(externalLoanDTO.getExternalLoanId(),
					externalLoanDTO);
			if (externalLoanDTO.getExternalLoanId() == 0) {
				newExternalLoans.add(externalLoanDTO);
			}
		}

		Date todaysDate = new Date();
		// update all existing and deleted loans
		for (ExternalLoan externalLoan : customer.getExternalLoans()) {
			if (updatedExternalLoansMap.containsKey(externalLoan
					.getExternalLoanId())) {
				ExternalLoanDTO externalLoanDTO = updatedExternalLoansMap
						.get(externalLoan.getExternalLoanId());
				BeanUtils.copyProperties(externalLoanDTO, externalLoan);
				externalLoan.setUpdatedBy(username);
				externalLoan.setUpdatedDate(todaysDate);
				getExternalLoanDAO().merge(externalLoan);
			} else {
				// delete this loan
				externalLoan.setActive(false);
				getExternalLoanDAO().merge(externalLoan);
			}
		}

		// add all new loans
		for (ExternalLoanDTO externalLoanDTO : newExternalLoans) {
			ExternalLoan externalLoan = new ExternalLoan();
			BeanUtils.copyProperties(externalLoanDTO, externalLoan);
			externalLoan.setExternalLoanId(null);
			externalLoan.setCreatedDate(todaysDate);
			externalLoan.setUpdatedDate(todaysDate);
			externalLoan.setCreatedBy(username);
			externalLoan.setUpdatedBy(username);
			externalLoan.setCustomer(customer);
			externalLoan.setActive(true);
			// map organization to external loan
			ExternalOrganization externalOrganization = getExternalOrganizationDAO()
					.findById(externalLoanDTO.getExternalOrganizationId());
			externalLoan.setExternalOrganization(externalOrganization);
			getExternalLoanDAO().persist(externalLoan);
			getExternalOrganizationDAO().merge(externalOrganization);
			customer.getExternalLoans().add(externalLoan);
		}

		getCustomerDAO().merge(customer);
		return getHelper().createCompleteActiveExternalLoanDTOs(
				customer.getExternalLoans());
	}
	
	/* (non-Javadoc)
	 * @see com.conflux.service.ICustomerService#entersWithPendingUpdate(int)
	 */
	@Override
	public List<CustomerDTO> getCentersWithPendingUpdate(int branchId) {
		List<Customer> centers = getCustomerDAO().findCentersWithClientsPendingUpdate(branchId);
		List<CustomerDTO> centerDTOs = new ArrayList<CustomerDTO>();
		for (Customer center : centers) {
			centerDTOs.add(getHelper().createCustomerDTO(center));
		}
		return centerDTOs;
	}

	@Override
	public List<CustomerDTO> getCustomersPendingUpdate(int centerId) {
		List<Customer> customers = getCustomerDAO().findClientsPendingUpdate(
				centerId);
		return populateCustomerDTOsList(customers);
	}

	@Override
	public void updateNewRating(int customerId, String newRating,
			String comment, String userName) {
		Customer customer = getCustomerDAO().findById(customerId);

		String currentRiskRating = customer.getRiskRating();
		customer.setRiskRating(newRating);
		customer.setUpdatedBy(userName);
		Date todaysDate = new Date();
		customer.setUpdatedDate(todaysDate);

		// add rating to risk rating history
		RiskRatingHistory ratingHistory = new RiskRatingHistory();
		ratingHistory.setComment(comment);
		ratingHistory.setCreatedBy(userName);
		ratingHistory.setCreatedDate(todaysDate);
		ratingHistory.setCustomer(customer);
		ratingHistory.setOriginalRating(currentRiskRating);
		ratingHistory.setUpdatedRating(newRating);
		getRiskRatingHistoryDAO().persist(ratingHistory);

		customer.getRiskRatingHistories().add(ratingHistory);
		getCustomerDAO().merge(customer);
	}

	
	/* (non-Javadoc)
	 * @see com.conflux.service.ICustomerService#updateNewBMRating(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateNewBMRating(int customerId, String newBmRating,
			String userName) {
		Customer customer = getCustomerDAO().findById(customerId);

		customer.setBmRating(newBmRating);
		customer.setUpdatedBy(userName);
		Date todaysDate = new Date();
		customer.setUpdatedDate(todaysDate);
		getCustomerDAO().merge(customer);
		
	}
	
	@Override
	public void updateNewBMProblemDescription(int customerId,
			String problemDescription, String userName) {
		Customer customer = getCustomerDAO().findById(customerId);
		customer.setBmProblemDescription(problemDescription);
		customer.setUpdatedBy(userName);
		Date todaysDate = new Date();
		customer.setUpdatedDate(todaysDate);
		getCustomerDAO().merge(customer);
	}
	
	@Override
	public void updateNewBMActionTaken(int customerId, String actionTaken,
			String userName) {
		Customer customer = getCustomerDAO().findById(customerId);
		customer.setBmActionTaken(actionTaken);
		customer.setUpdatedBy(userName);
		Date todaysDate = new Date();
		customer.setUpdatedDate(todaysDate);
		getCustomerDAO().merge(customer);
	}

	/* (non-Javadoc)
	 * @see com.conflux.service.ICustomerService#updatePendingStatus(int, java.lang.String)
	 */
	@Override
	public void updatePendingStatus(int customerId, String userName) {
		Customer customer = getCustomerDAO().findById(customerId);

		customer.setPendingUpdate(false);
		customer.setUpdatedBy(userName);
		Date todaysDate = new Date();
		customer.setUpdatedDate(todaysDate);
		getCustomerDAO().merge(customer);

	}

	private List<CustomerDTO> populateCustomerDTOsList(List<Customer> customers) {
		List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
		for (Customer customer : customers) {
			CustomerDTO customerDTO = getHelper().createCustomerDTO(customer);
			customerDTOs.add(customerDTO);
		}
		return customerDTOs;
	}

	@Override
	public CustomerDTO getBasicCustomerInformationById(int customerId) {
		Customer customer = getCustomerDAO().findById(customerId);
		return getHelper().createCustomerDTO(customer);
	}

}
