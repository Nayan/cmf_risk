package com.conflux.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.OccupationSubTypeDTO;

@ManagedBean(name = "occupationTypeBean")
@ViewScoped
public class OccupationTypeManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	@ManagedProperty(value = "#{appBean}")
	private ApplicationManagedBean appBean;

	private int occupationTypeId;
	private int officeId;
	private List<OccupationSubTypeDTO> occupationSubTypes;
	private OccupationSubTypeDTO occupationSubTypeDTO = new OccupationSubTypeDTO();
	private int sourceBranchId;
	private int destinationBranchId;
	private DualListModel<OccupationSubTypeDTO> dualOccupationSubTypes;
	private List<OccupationSubTypeDTO> sourceOccupationSubTypes = new ArrayList<OccupationSubTypeDTO>();
	private List<OccupationSubTypeDTO> destinationOccupationSubTypes = new ArrayList<OccupationSubTypeDTO>();

	@PostConstruct
	public void init() {
		dualOccupationSubTypes = new DualListModel<OccupationSubTypeDTO>(
				sourceOccupationSubTypes, destinationOccupationSubTypes);
	}

	public void populateOccupationSubTypes() {
		if (officeId != 0 && occupationTypeId != 0) {
			occupationSubTypes = getOccupationTypeService()
					.getOccupationSubTypes(officeId, occupationTypeId);
		}
	}

	public void resetOccupationSubType() {
		occupationSubTypeDTO = new OccupationSubTypeDTO();
	}

	public void addOccupationSubType() {
		// check if multiplier label is required
		if (occupationSubTypeDTO.isMultiplierPresent()) {
			if (occupationSubTypeDTO.getMultiplierLabel() == null
					|| occupationSubTypeDTO.getMultiplierLabel().trim()
							.equalsIgnoreCase("")) {
				addFacesError("Please enter the label of the multiplier field");
				return;
			}
		}
		// check if additional field label is required
		if (occupationSubTypeDTO.isAdditionalFieldPresent()) {
			if (occupationSubTypeDTO.getAdditionalFieldLabel() == null
					|| occupationSubTypeDTO.getAdditionalFieldLabel().trim()
							.equalsIgnoreCase("")) {
				addFacesError("Please enter the label for the additional field");
				return;
			}
		}
		/***
		 * Ensure that another occupation subtype with the same name is not
		 * present in the current Occupation Type and Branch combination
		 **/
		if (getOccupationTypeService().existsOccupationSubType(
				occupationSubTypeDTO.getName(), officeId, occupationTypeId)) {
			addFacesError("Another occupation subtype with the same name exists within the selected Branch and Occupation Type");
			return;
		}
		// set audit trail
		occupationSubTypeDTO.setCreatedBy(getLoginBean().getUserT()
				.getUsername());
		occupationSubTypeDTO.setUpdatedBy(getLoginBean().getUserT()
				.getUsername());

		// save the new occupation subtype
		occupationSubTypeDTO = getOccupationTypeService()
				.createOccupationSubType(occupationSubTypeDTO, officeId,
						occupationTypeId);

		occupationSubTypes.add(0, occupationSubTypeDTO);
		occupationSubTypeDTO = new OccupationSubTypeDTO();

	}

	public void removeOccupationSubType(
			OccupationSubTypeDTO occupationSubTypeDTO) {
		getOccupationTypeService().deleteOccupationSubType(
				occupationSubTypeDTO.getOccupationSubTypeId(),
				getLoginBean().getUserT().getUsername());
		occupationSubTypes.remove(occupationSubTypeDTO);
	}

	public void editOccupationSubType(RowEditEvent event) {
		OccupationSubTypeDTO editedOccupationSubTypeDTO = (OccupationSubTypeDTO) event
				.getObject();

		// check if multiplier label is required
		if (editedOccupationSubTypeDTO.isMultiplierPresent()) {
			if (editedOccupationSubTypeDTO.getMultiplierLabel() == null
					|| editedOccupationSubTypeDTO.getMultiplierLabel().trim()
							.equalsIgnoreCase("")) {
				addFacesError("Please enter the label of the multiplier field");
				return;
			}
		}
		// check if additional field label is required
		if (editedOccupationSubTypeDTO.isAdditionalFieldPresent()) {
			if (editedOccupationSubTypeDTO.getAdditionalFieldLabel() == null
					|| editedOccupationSubTypeDTO.getAdditionalFieldLabel()
							.trim().equalsIgnoreCase("")) {
				addFacesError("Please enter the label for the additional field");
				return;
			}
		}
		// set audit trail
		editedOccupationSubTypeDTO.setUpdatedBy(getLoginBean().getUserT()
				.getUsername());

		getOccupationTypeService().updateOccupationSubType(
				editedOccupationSubTypeDTO);

	}

	public void populateOccupationSubTypesForSourceBranch() {
		if (sourceBranchId != 0 && occupationTypeId != 0) {
			sourceOccupationSubTypes = getOccupationTypeService()
					.getOccupationSubTypes(sourceBranchId, occupationTypeId);
			dualOccupationSubTypes = new DualListModel<OccupationSubTypeDTO>(
					sourceOccupationSubTypes, destinationOccupationSubTypes);
		}
	}

	public void populateOccupationSubTypesForDestBranch() {
		if (destinationBranchId != 0 && occupationTypeId != 0) {
			destinationOccupationSubTypes = getOccupationTypeService()
					.getOccupationSubTypes(destinationBranchId,
							occupationTypeId);
			dualOccupationSubTypes = new DualListModel<OccupationSubTypeDTO>(
					sourceOccupationSubTypes, destinationOccupationSubTypes);
		}
	}

	public void populateOccupationSubTypesForSourceAndDestinationBranch() {
		populateOccupationSubTypesForSourceBranch();
		populateOccupationSubTypesForDestBranch();
	}

	public String saveOccupationSubTypesForDestinationBranch() {
		List<OccupationSubTypeDTO> newOccupationSubTypeDTOs = new ArrayList<OccupationSubTypeDTO>();

		// ensure source and destination branches are not the same
		if (sourceBranchId == destinationBranchId) {
			addFacesError("Source and Destination branch for copying occupation subtypes must be unique");
			return null;
		}

		for (OccupationSubTypeDTO subTypeDTO : dualOccupationSubTypes
				.getTarget()) {
			if (subTypeDTO.getBranchId() == sourceBranchId) {
				subTypeDTO
						.setUpdatedBy(getLoginBean().getUserT().getUsername());
				subTypeDTO
						.setCreatedBy(getLoginBean().getUserT().getUsername());
				newOccupationSubTypeDTOs.add(subTypeDTO);
			}
		}

		getOccupationTypeService()
				.createOccupationSubTypes(newOccupationSubTypeDTOs,
						destinationBranchId, occupationTypeId);

		return ApplicationConstants.GENERIC_NAVIGATION.ADMIN.toString();
	}

	public int getOccupationTypeId() {
		return occupationTypeId;
	}

	public void setOccupationTypeId(int occupationTypeId) {
		this.occupationTypeId = occupationTypeId;
	}

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	public List<OccupationSubTypeDTO> getOccupationSubTypes() {
		return occupationSubTypes;
	}

	public void setOccupationSubTypes(
			List<OccupationSubTypeDTO> occupationSubTypes) {
		this.occupationSubTypes = occupationSubTypes;
	}

	public OccupationSubTypeDTO getOccupationSubTypeDTO() {
		return occupationSubTypeDTO;
	}

	public void setOccupationSubTypeDTO(
			OccupationSubTypeDTO occupationSubTypeDTO) {
		this.occupationSubTypeDTO = occupationSubTypeDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getSourceBranchId() {
		return sourceBranchId;
	}

	public void setSourceBranchId(int sourceBranchId) {
		this.sourceBranchId = sourceBranchId;
	}

	public int getDestinationBranchId() {
		return destinationBranchId;
	}

	public void setDestinationBranchId(int destinationBranchId) {
		this.destinationBranchId = destinationBranchId;
	}

	public DualListModel<OccupationSubTypeDTO> getDualOccupationSubTypes() {
		return dualOccupationSubTypes;
	}

	public void setDualOccupationSubTypes(
			DualListModel<OccupationSubTypeDTO> dualOccupationSubTypes) {
		this.dualOccupationSubTypes = dualOccupationSubTypes;
	}

	public List<OccupationSubTypeDTO> getSourceOccupationSubTypes() {
		return sourceOccupationSubTypes;
	}

	public void setSourceOccupationSubTypes(
			List<OccupationSubTypeDTO> sourceOccupationSubTypes) {
		this.sourceOccupationSubTypes = sourceOccupationSubTypes;
	}

	public List<OccupationSubTypeDTO> getDestinationOccupationSubTypes() {
		return destinationOccupationSubTypes;
	}

	public void setDestinationOccupationSubTypes(
			List<OccupationSubTypeDTO> destinationOccupationSubTypes) {
		this.destinationOccupationSubTypes = destinationOccupationSubTypes;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public ApplicationConstants.STABILITY_FACTOR[] getStabilityFactors() {
		return ApplicationConstants.STABILITY_FACTOR.values();
	}
}
