package com.conflux.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.AuditTypeDTO;
import com.conflux.service.dto.QuestionDTO;

@ManagedBean(name = "auditTypeBean")
@SessionScoped
public class AuditTypeManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	@ManagedProperty(value = "#{appBean}")
	ApplicationManagedBean appBean;

	private AuditTypeDTO auditTypeDTO = new AuditTypeDTO();
	private QuestionDTO questionDTO = new QuestionDTO();
	private List<AuditTypeDTO> auditTypeDTOs;
	private boolean editCurrentAudit;

	private void reset(){
		auditTypeDTO = new AuditTypeDTO();
		questionDTO = new QuestionDTO();
		editCurrentAudit = false;
	}
	
	private boolean validateTransalationMatrix() {
		for (int i = 1; i < 5; i++) {
			int previousMaxScore = auditTypeDTO.getAuditTypeScoringDTOs()
					.get(i - 1).getMaxScore();
			int currentMinScore = auditTypeDTO.getAuditTypeScoringDTOs().get(i)
					.getMinScore();
			int currentMaxScore = auditTypeDTO.getAuditTypeScoringDTOs().get(i)
					.getMinScore();
			if ((previousMaxScore + 1) != currentMinScore) {
				addFacesError("Please ensure that the Maximum score of one rating bucket does not exceed the minimum score of the next");
				return false;
			} else if (currentMaxScore < currentMinScore) {
				addFacesError("Within a rating bucket, the max score must be greater than the minimum score");
				return false;
			}
		}
		return true;
	}

	public void resetQuestion() {
		this.questionDTO = new QuestionDTO();
	}

	public void addQuestion() {
		/** check boundary values for range questions **/
		if (!this.questionDTO.isNegativeScore()) {
			if (!(questionDTO.getMaxScore() >= questionDTO.getMinScore())) {
				addFacesError("Max score must be greater than or equal to the Min score");
				return;
			} else if (!(questionDTO.getMaxScore() >= questionDTO
					.getDefaultScore() && questionDTO.getDefaultScore() >= questionDTO
					.getMinScore())) {
				addFacesError("Default score must be between Max and Min scores");
				return;
			}
		}
		questionDTO.setCreatedBy(getLoginBean().getUserT().getUsername());
		questionDTO.setUpdatedBy(getLoginBean().getUserT().getUsername());
		Date todaysDate = new Date();
		questionDTO.setCreatedDate(todaysDate);
		questionDTO.setUpdatedDate(todaysDate);

		getAuditTypeDTO().getQuestionDTOs().add(questionDTO);
		/*** rest question **/
		resetQuestion();
	}

	public void removeQuestion(QuestionDTO questionDTO) {
		auditTypeDTO.getQuestionDTOs().remove(questionDTO);
	}

	// TODO This functionality should be in a validator
	public void editQuestion(RowEditEvent event) {
		QuestionDTO questionDTO = (QuestionDTO) event.getObject();
		/** check boundary values for range questions **/
		if (!this.questionDTO.isNegativeScore()) {
			if (!(questionDTO.getMaxScore() >= questionDTO.getMinScore())) {
				addFacesError("Max score must be greater than or equal to the Min score");
				return;
			} else if (!(questionDTO.getMaxScore() >= questionDTO
					.getDefaultScore() && questionDTO.getDefaultScore() >= questionDTO
					.getMinScore())) {
				addFacesError("Default score must be between Max and Min scores");
				return;
			}
		}

	}

	public void incrementQuestionPosition(QuestionDTO selectedQuestionDTO) {
		int position = auditTypeDTO.getQuestionDTOs().indexOf(
				selectedQuestionDTO);
		/**
		 * Ensure list has more than 1 element and selected element is not the
		 * first in the list
		 **/
		if (auditTypeDTO.getQuestionDTOs().size() > 1 && position > 0) {
			auditTypeDTO.getQuestionDTOs().remove(selectedQuestionDTO);
			auditTypeDTO.getQuestionDTOs().add(position - 1,
					selectedQuestionDTO);
		}
	}

	public void decrementQuestionPosition(QuestionDTO selectedQuestionDTO) {
		int position = auditTypeDTO.getQuestionDTOs().indexOf(
				selectedQuestionDTO);
		/**
		 * Ensure list has more than 1 element and selected element is not the
		 * last in the list
		 **/
		if (auditTypeDTO.getQuestionDTOs().size() > 1
				&& position <= (auditTypeDTO.getQuestionDTOs().size() - 1)) {
			auditTypeDTO.getQuestionDTOs().remove(selectedQuestionDTO);
			if (position == auditTypeDTO.getQuestionDTOs().size()) {
				auditTypeDTO.getQuestionDTOs().add(selectedQuestionDTO);
			} else {
				auditTypeDTO.getQuestionDTOs().add(position + 1,
						selectedQuestionDTO);
			}
		}
	}

	public void populateBucketMinValues() {
		for (int i = 1; i < 5; i++) {
			auditTypeDTO
					.getAuditTypeScoringDTOs()
					.get(i)
					.setMinScore(
							auditTypeDTO.getAuditTypeScoringDTOs().get(i - 1)
									.getMaxScore() + 1);
		}

	}
	
	public String newAuditType(){
		reset();
		return ApplicationConstants.ADMIN_NAVIGATION.CREATE_AUDIT.toString();
	}
	
	public String createAuditType() {
		/** Validate translation matrix **/
		boolean verifyTransalationMatrix = validateTransalationMatrix();
		if (!verifyTransalationMatrix) {
			return null;
		}
		/**
		 * Check that another Audit Type with the same name does not exist for
		 * the same entity Type
		 **/
		boolean auditProcessNameExists = getAuditTypeService()
				.existsAuditTypeByName(auditTypeDTO.getName(),
						auditTypeDTO.getOrgEntityId());
		if (auditProcessNameExists) {
			addFacesError("An audit Process with the same name exists for the given Audit Type");
			return null;
		}
		/** Ensures the audit has atleast one question defined in it **/
		if (auditTypeDTO.getQuestionDTOs().size() <= 0) {
			addFacesError("An audit Process should have atleast one question defined in it");
			return null;
		}
		/** Sets all audit traces for the object **/
		auditTypeDTO.setCreatedBy(getLoginBean().getUserT().getUsername());
		auditTypeDTO.setUpdatedBy(getLoginBean().getUserT().getUsername());

		/** create this audit **/
		getAuditTypeService().createAuditType(auditTypeDTO);

		/** Set an info **/
		addFacesInfo("A new Audit process with the name "
				+ auditTypeDTO.getName() + " has been created");

		return ApplicationConstants.GENERIC_NAVIGATION.ADMIN.toString();
	}

	public String viewAllAuditTypes() {
		reset();
		auditTypeDTOs = getAuditTypeService().getAllAuditTypes();
		return ApplicationConstants.ADMIN_NAVIGATION.VIEW_ALL_AUDIT_TYPES
				.toString();
	}

	public String viewAuditType(String auditTypeId) {
		reset();
		auditTypeDTO = getAuditTypeService().getAuditType(
				Integer.parseInt(auditTypeId));
		editCurrentAudit = false;
		return ApplicationConstants.ADMIN_NAVIGATION.VIEW_AUDIT_TYPE.toString();
	}

	public String editAuditType(int auditTypeId) {
		reset();
		auditTypeDTO = getAuditTypeService().getAuditType(auditTypeId);
		editCurrentAudit = true;
		return ApplicationConstants.ADMIN_NAVIGATION.EDIT_AUDIT.toString();
	}

	public String updateAuditType() {
		auditTypeDTO.setUpdatedBy(getLoginBean().getUserT().getUsername());
		getAuditTypeService().updateAuditType(auditTypeDTO,
				getLoginBean().getUserT().getUsername());
		return ApplicationConstants.ADMIN_NAVIGATION.VIEW_ALL_AUDIT_TYPES.toString();
	}

	public void delete(int auditTypeId){
		int index = -1;
		for (AuditTypeDTO auditTypeDTO : auditTypeDTOs) {
			if(auditTypeDTO.getAuditTypeId() == auditTypeId){
				index = auditTypeDTOs.indexOf(auditTypeDTO);
				break;
			}
		}
		if(index != -1){
			auditTypeDTOs.remove(index);
		}
	}
	
	public AuditTypeDTO getAuditTypeDTO() {
		return auditTypeDTO;
	}

	public void setAuditTypeDTO(AuditTypeDTO auditTypeDTO) {
		this.auditTypeDTO = auditTypeDTO;
	}

	public QuestionDTO getQuestionDTO() {
		return questionDTO;
	}

	public void setQuestionDTO(QuestionDTO questionDTO) {
		this.questionDTO = questionDTO;
	}

	public List<AuditTypeDTO> getAuditTypeDTOs() {
		return auditTypeDTOs;
	}

	public void setAuditTypeDTOs(List<AuditTypeDTO> auditTypeDTOs) {
		this.auditTypeDTOs = auditTypeDTOs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public boolean isEditCurrentAudit() {
		return editCurrentAudit;
	}

	public void setEditCurrentAudit(boolean editCurrentAudit) {
		this.editCurrentAudit = editCurrentAudit;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}
}
