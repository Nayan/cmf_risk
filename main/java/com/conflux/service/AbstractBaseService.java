package com.conflux.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.conflux.dal.dao.IAssetDAO;
import com.conflux.dal.dao.IAssetSubTypeDAO;
import com.conflux.dal.dao.IAssetTypeDAO;
import com.conflux.dal.dao.IAuditDAO;
import com.conflux.dal.dao.IAuditQuestionDAO;
import com.conflux.dal.dao.IAuditTypeDAO;
import com.conflux.dal.dao.IAuditTypeScoringDAO;
import com.conflux.dal.dao.ICustomerDAO;
import com.conflux.dal.dao.ICustomerFamilyMemberDAO;
import com.conflux.dal.dao.ICustomerFamilyOccupationDAO;
import com.conflux.dal.dao.ICustomerTypeDAO;
import com.conflux.dal.dao.IEmployeeDAO;
import com.conflux.dal.dao.IExternalLoanDAO;
import com.conflux.dal.dao.IExternalOrganizationDAO;
import com.conflux.dal.dao.IIncomeLumpingDAO;
import com.conflux.dal.dao.ILoanAccountDAO;
import com.conflux.dal.dao.ILoanProductDAO;
import com.conflux.dal.dao.ILoanPurposeDAO;
import com.conflux.dal.dao.ILoanStatusDAO;
import com.conflux.dal.dao.ILoanUtilizationCheckDAO;
import com.conflux.dal.dao.IMailSenderDAO;
import com.conflux.dal.dao.IMeetingDAO;
import com.conflux.dal.dao.IOccupationDAO;
import com.conflux.dal.dao.IOccupationSubTypeDAO;
import com.conflux.dal.dao.IOccupationTypeDAO;
import com.conflux.dal.dao.IOfficeDAO;
import com.conflux.dal.dao.IOfficeTypeDAO;
import com.conflux.dal.dao.IOrgEntityDAO;
import com.conflux.dal.dao.IPortalUserDAO;
import com.conflux.dal.dao.IQuestionDAO;
import com.conflux.dal.dao.IRatingBucketDAO;
import com.conflux.dal.dao.IRelationshipTypeDAO;
import com.conflux.dal.dao.IRiskRatingHistoryDAO;
import com.conflux.dal.dao.IUserRoleDAO;

public class AbstractBaseService {

	@Autowired
	private ServiceHelper helper;
	@Autowired
	private IAssetDAO assetDAO;
	@Autowired
	private IAssetSubTypeDAO assetSubTypeDAO;
	@Autowired
	private IAssetTypeDAO assetTypeDAO;
	@Autowired
	private IAuditDAO auditDAO;
	@Autowired
	private IAuditQuestionDAO auditQuestionDAO;
	@Autowired
	private IAuditTypeDAO auditTypeDAO;
	@Autowired
	private IAuditTypeScoringDAO auditTypeScoringDAO;
	@Autowired
	private ICustomerDAO customerDAO;
	@Autowired
	private ICustomerFamilyMemberDAO customerFamilyMemberDAO;
	@Autowired
	private ICustomerFamilyOccupationDAO customerFamilyOccupationDAO;
	@Autowired
	private ICustomerTypeDAO customerTypeDAO;
	@Autowired
	private IEmployeeDAO employeeDAO;
	@Autowired
	private IExternalLoanDAO externalLoanDAO;
	@Autowired
	private IExternalOrganizationDAO externalOrganizationDAO;
	@Autowired
	private ILoanAccountDAO loanAccountDAO;
	@Autowired
	private ILoanProductDAO loanProductDAO;
	@Autowired
	private ILoanPurposeDAO loanPurposeDAO;
	@Autowired
	private ILoanStatusDAO loanStatusDAO;
	@Autowired
	private ILoanUtilizationCheckDAO loanUtilizationCheckDAO;
	@Autowired
	private IMailSenderDAO mailSenderDAO;
	@Autowired
	private IMeetingDAO meetingDAO;
	@Autowired
	private IOccupationDAO occupationDAO;
	@Autowired
	private IOccupationSubTypeDAO occupationSubTypeDAO;
	@Autowired
	private IOccupationTypeDAO occupationTypeDAO;
	@Autowired
	private IOfficeDAO officeDAO;
	@Autowired
	private IOfficeTypeDAO officeTypeDAO;
	@Autowired
	private IOrgEntityDAO orgEntityDAO;
	@Autowired
	private IPortalUserDAO portalUserDAO;
	@Autowired
	private IQuestionDAO questionDAO;
	@Autowired
	private IRatingBucketDAO ratingBucketDAO;
	@Autowired
	private IRelationshipTypeDAO relationshipTypeDAO;
	@Autowired
	private IRiskRatingHistoryDAO riskRatingHistoryDAO;
	@Autowired
	private IUserRoleDAO userRoleDAO;
	@Autowired
	private IIncomeLumpingDAO incomeLumpingDAO;

	public ServiceHelper getHelper() {
		return helper;
	}

	public void setHelper(ServiceHelper helper) {
		this.helper = helper;
	}

	public IAssetDAO getAssetDAO() {
		return assetDAO;
	}

	public void setAssetDAO(IAssetDAO assetDAO) {
		this.assetDAO = assetDAO;
	}

	public IAssetSubTypeDAO getAssetSubTypeDAO() {
		return assetSubTypeDAO;
	}

	public void setAssetSubTypeDAO(IAssetSubTypeDAO assetSubTypeDAO) {
		this.assetSubTypeDAO = assetSubTypeDAO;
	}

	public IAssetTypeDAO getAssetTypeDAO() {
		return assetTypeDAO;
	}

	public void setAssetTypeDAO(IAssetTypeDAO assetTypeDAO) {
		this.assetTypeDAO = assetTypeDAO;
	}

	public IAuditDAO getAuditDAO() {
		return auditDAO;
	}

	public void setAuditDAO(IAuditDAO auditDAO) {
		this.auditDAO = auditDAO;
	}

	public IAuditQuestionDAO getAuditQuestionDAO() {
		return auditQuestionDAO;
	}

	public void setAuditQuestionDAO(IAuditQuestionDAO auditQuestionDAO) {
		this.auditQuestionDAO = auditQuestionDAO;
	}

	public IAuditTypeDAO getAuditTypeDAO() {
		return auditTypeDAO;
	}

	public void setAuditTypeDAO(IAuditTypeDAO auditTypeDAO) {
		this.auditTypeDAO = auditTypeDAO;
	}

	public IAuditTypeScoringDAO getAuditTypeScoringDAO() {
		return auditTypeScoringDAO;
	}

	public void setAuditTypeScoringDAO(IAuditTypeScoringDAO auditTypeScoringDAO) {
		this.auditTypeScoringDAO = auditTypeScoringDAO;
	}

	public ICustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(ICustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public ICustomerFamilyMemberDAO getCustomerFamilyMemberDAO() {
		return customerFamilyMemberDAO;
	}

	public void setCustomerFamilyMemberDAO(
			ICustomerFamilyMemberDAO customerFamilyMemberDAO) {
		this.customerFamilyMemberDAO = customerFamilyMemberDAO;
	}

	public ICustomerFamilyOccupationDAO getCustomerFamilyOccupationDAO() {
		return customerFamilyOccupationDAO;
	}

	public void setCustomerFamilyOccupationDAO(
			ICustomerFamilyOccupationDAO customerFamilyOccupationDAO) {
		this.customerFamilyOccupationDAO = customerFamilyOccupationDAO;
	}

	public ICustomerTypeDAO getCustomerTypeDAO() {
		return customerTypeDAO;
	}

	public void setCustomerTypeDAO(ICustomerTypeDAO customerTypeDAO) {
		this.customerTypeDAO = customerTypeDAO;
	}

	public IEmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(IEmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public IExternalLoanDAO getExternalLoanDAO() {
		return externalLoanDAO;
	}

	public void setExternalLoanDAO(IExternalLoanDAO externalLoanDAO) {
		this.externalLoanDAO = externalLoanDAO;
	}

	public IExternalOrganizationDAO getExternalOrganizationDAO() {
		return externalOrganizationDAO;
	}

	public void setExternalOrganizationDAO(
			IExternalOrganizationDAO externalOrganizationDAO) {
		this.externalOrganizationDAO = externalOrganizationDAO;
	}

	public ILoanAccountDAO getLoanAccountDAO() {
		return loanAccountDAO;
	}

	public void setLoanAccountDAO(ILoanAccountDAO loanAccountDAO) {
		this.loanAccountDAO = loanAccountDAO;
	}

	public ILoanProductDAO getLoanProductDAO() {
		return loanProductDAO;
	}

	public void setLoanProductDAO(ILoanProductDAO loanProductDAO) {
		this.loanProductDAO = loanProductDAO;
	}

	public ILoanPurposeDAO getLoanPurposeDAO() {
		return loanPurposeDAO;
	}

	public void setLoanPurposeDAO(ILoanPurposeDAO loanPurposeDAO) {
		this.loanPurposeDAO = loanPurposeDAO;
	}

	public ILoanStatusDAO getLoanStatusDAO() {
		return loanStatusDAO;
	}

	public void setLoanStatusDAO(ILoanStatusDAO loanStatusDAO) {
		this.loanStatusDAO = loanStatusDAO;
	}

	public ILoanUtilizationCheckDAO getLoanUtilizationCheckDAO() {
		return loanUtilizationCheckDAO;
	}

	public void setLoanUtilizationCheckDAO(
			ILoanUtilizationCheckDAO loanUtilizationCheckDAO) {
		this.loanUtilizationCheckDAO = loanUtilizationCheckDAO;
	}

	public IMailSenderDAO getMailSenderDAO() {
		return mailSenderDAO;
	}

	public void setMailSenderDAO(IMailSenderDAO mailSenderDAO) {
		this.mailSenderDAO = mailSenderDAO;
	}

	public IMeetingDAO getMeetingDAO() {
		return meetingDAO;
	}

	public void setMeetingDAO(IMeetingDAO meetingDAO) {
		this.meetingDAO = meetingDAO;
	}

	public IOccupationDAO getOccupationDAO() {
		return occupationDAO;
	}

	public void setOccupationDAO(IOccupationDAO occupationDAO) {
		this.occupationDAO = occupationDAO;
	}

	public IOccupationSubTypeDAO getOccupationSubTypeDAO() {
		return occupationSubTypeDAO;
	}

	public void setOccupationSubTypeDAO(
			IOccupationSubTypeDAO occupationSubTypeDAO) {
		this.occupationSubTypeDAO = occupationSubTypeDAO;
	}

	public IOccupationTypeDAO getOccupationTypeDAO() {
		return occupationTypeDAO;
	}

	public void setOccupationTypeDAO(IOccupationTypeDAO occupationTypeDAO) {
		this.occupationTypeDAO = occupationTypeDAO;
	}

	public IOfficeDAO getOfficeDAO() {
		return officeDAO;
	}

	public void setOfficeDAO(IOfficeDAO officeDAO) {
		this.officeDAO = officeDAO;
	}

	public IOfficeTypeDAO getOfficeTypeDAO() {
		return officeTypeDAO;
	}

	public void setOfficeTypeDAO(IOfficeTypeDAO officeTypeDAO) {
		this.officeTypeDAO = officeTypeDAO;
	}

	public IOrgEntityDAO getOrgEntityDAO() {
		return orgEntityDAO;
	}

	public void setOrgEntityDAO(IOrgEntityDAO orgEntityDAO) {
		this.orgEntityDAO = orgEntityDAO;
	}

	public IPortalUserDAO getPortalUserDAO() {
		return portalUserDAO;
	}

	public void setPortalUserDAO(IPortalUserDAO portalUserDAO) {
		this.portalUserDAO = portalUserDAO;
	}

	public IQuestionDAO getQuestionDAO() {
		return questionDAO;
	}

	public void setQuestionDAO(IQuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public IRatingBucketDAO getRatingBucketDAO() {
		return ratingBucketDAO;
	}

	public void setRatingBucketDAO(IRatingBucketDAO ratingBucketDAO) {
		this.ratingBucketDAO = ratingBucketDAO;
	}

	public IRelationshipTypeDAO getRelationshipTypeDAO() {
		return relationshipTypeDAO;
	}

	public void setRelationshipTypeDAO(IRelationshipTypeDAO relationshipTypeDAO) {
		this.relationshipTypeDAO = relationshipTypeDAO;
	}

	public IRiskRatingHistoryDAO getRiskRatingHistoryDAO() {
		return riskRatingHistoryDAO;
	}

	public void setRiskRatingHistoryDAO(
			IRiskRatingHistoryDAO riskRatingHistoryDAO) {
		this.riskRatingHistoryDAO = riskRatingHistoryDAO;
	}

	public IUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(IUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public IIncomeLumpingDAO getIncomeLumpingDAO() {
		return incomeLumpingDAO;
	}

	public void setIncomeLumpingDAO(IIncomeLumpingDAO incomeLumpingDAO) {
		this.incomeLumpingDAO = incomeLumpingDAO;
	}

}
