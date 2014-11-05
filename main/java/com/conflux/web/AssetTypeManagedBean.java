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
import com.conflux.service.dto.AssetSubTypeDTO;
import com.conflux.service.dto.OccupationSubTypeDTO;

@ManagedBean(name = "assetTypeBean")
@ViewScoped
public class AssetTypeManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	private int assetTypeId;
	private int branchId;
	private List<AssetSubTypeDTO> assetSubTypes;
	private AssetSubTypeDTO assetSubTypeDTO = new AssetSubTypeDTO();
	private int sourceBranchId;
	private int destinationBranchId;
	private DualListModel<AssetSubTypeDTO> dualAssetSubTypes;
	private List<AssetSubTypeDTO> sourceAssetSubTypes = new ArrayList<AssetSubTypeDTO>();
	private List<AssetSubTypeDTO> destinationAssetSubTypes = new ArrayList<AssetSubTypeDTO>();

	@ManagedProperty(value = "#{appBean}")
	ApplicationManagedBean appBean;

	@PostConstruct
	public void init() {
		dualAssetSubTypes = new DualListModel<AssetSubTypeDTO>(
				sourceAssetSubTypes, destinationAssetSubTypes);
	}

	public void populateAssetSubTypes() {
		if (branchId != 0 && assetTypeId != 0) {
			assetSubTypes = getAssetTypeService().getAssetSubTypes(branchId,
					assetTypeId);
		}
	}

	public void resetAssetSubType() {
		assetSubTypeDTO = new AssetSubTypeDTO();
	}

	public void addAssetSubType() {
		// check if additional field label is required
		if (assetSubTypeDTO.isAdditionalFieldPresent()) {
			if (assetSubTypeDTO.getAdditionalFieldLabel() == null
					|| assetSubTypeDTO.getAdditionalFieldLabel().trim()
							.equalsIgnoreCase("")) {
				addFacesError("Please enter the label for the additional field");
				return;
			}
		}
		/***
		 * Ensure that another asset subtype with the same name is not present
		 * in the current Asset Type and Branch combination
		 **/
		if (getAssetTypeService().existsAssetSubType(assetSubTypeDTO.getName(),
				branchId, assetTypeId)) {
			addFacesError("Another asset subtype with the same name exists within the selected Branch and Asset Type");
			return;
		}
		// set audit trail
		assetSubTypeDTO.setCreatedBy(getLoginBean().getUserT().getUsername());
		assetSubTypeDTO.setUpdatedBy(getLoginBean().getUserT().getUsername());

		// save the new asset subtype
		assetSubTypeDTO = getAssetTypeService().createAssetSubType(
				assetSubTypeDTO, branchId, assetTypeId);

		assetSubTypes.add(0, assetSubTypeDTO);
		assetSubTypeDTO = new AssetSubTypeDTO();

	}

	public void removeAssetSubType(AssetSubTypeDTO assetSubTypeDTO) {
		getAssetTypeService().deleteAssetSubType(
				assetSubTypeDTO.getAssetSubTypeId(),
				getLoginBean().getUserT().getUsername());
		assetSubTypes.remove(assetSubTypeDTO);
	}

	public void editAssetSubType(RowEditEvent event) {
		AssetSubTypeDTO editedAssetSubTypeDTO = (AssetSubTypeDTO) event
				.getObject();

		// check if additional field label is required
		if (editedAssetSubTypeDTO.isAdditionalFieldPresent()) {
			if (editedAssetSubTypeDTO.getAdditionalFieldLabel() == null
					|| editedAssetSubTypeDTO.getAdditionalFieldLabel().trim()
							.equalsIgnoreCase("")) {
				addFacesError("Please enter the label for the additional field");
				return;
			}
		}
		// set audit trail
		editedAssetSubTypeDTO.setUpdatedBy(getLoginBean().getUserT()
				.getUsername());

		getAssetTypeService().updateAssetSubType(editedAssetSubTypeDTO);
	}

	public void populateAssetSubTypesForSourceBranch() {
		if (sourceBranchId != 0 && assetTypeId != 0) {
			sourceAssetSubTypes = getAssetTypeService().getAssetSubTypes(
					sourceBranchId, assetTypeId);
			dualAssetSubTypes = new DualListModel<AssetSubTypeDTO>(
					sourceAssetSubTypes, destinationAssetSubTypes);
		}
	}

	public void populateAssetSubTypesForDestBranch() {
		if (destinationBranchId != 0 && assetTypeId != 0) {
			destinationAssetSubTypes = getAssetTypeService().getAssetSubTypes(
					destinationBranchId, assetTypeId);
			dualAssetSubTypes = new DualListModel<AssetSubTypeDTO>(
					sourceAssetSubTypes, destinationAssetSubTypes);
		}
	}

	public void populateAssetSubTypesForSourceAndDestinationBranch() {
		populateAssetSubTypesForSourceBranch();
		populateAssetSubTypesForDestBranch();
	}

	public String saveAssetSubTypesForDestinationBranch() {
		List<AssetSubTypeDTO> newAssetSubTypeDTOs = new ArrayList<AssetSubTypeDTO>();

		// ensure source and destination branches are not the same
		if (sourceBranchId == destinationBranchId) {
			addFacesError("Source and Destination branch for copying asset subtypes must be unique");
			return null;

		}

		for (AssetSubTypeDTO subTypeDTO : dualAssetSubTypes.getTarget()) {
			if (subTypeDTO.getBranchId() == sourceBranchId) {
				subTypeDTO
						.setUpdatedBy(getLoginBean().getUserT().getUsername());
				subTypeDTO
						.setCreatedBy(getLoginBean().getUserT().getUsername());
				newAssetSubTypeDTOs.add(subTypeDTO);
			}
		}

		getAssetTypeService().createAssetSubTypes(newAssetSubTypeDTOs,
				destinationBranchId, assetTypeId);

		return ApplicationConstants.GENERIC_NAVIGATION.ADMIN.toString();
	}

	public int getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public List<AssetSubTypeDTO> getAssetSubTypes() {
		return assetSubTypes;
	}

	public void setAssetSubTypes(List<AssetSubTypeDTO> assetSubTypes) {
		this.assetSubTypes = assetSubTypes;
	}

	public AssetSubTypeDTO getAssetSubTypeDTO() {
		return assetSubTypeDTO;
	}

	public void setAssetSubTypeDTO(AssetSubTypeDTO assetSubTypeDTO) {
		this.assetSubTypeDTO = assetSubTypeDTO;
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

	public DualListModel<AssetSubTypeDTO> getDualAssetSubTypes() {
		return dualAssetSubTypes;
	}

	public void setDualAssetSubTypes(
			DualListModel<AssetSubTypeDTO> dualAssetSubTypes) {
		this.dualAssetSubTypes = dualAssetSubTypes;
	}

	public List<AssetSubTypeDTO> getSourceAssetSubTypes() {
		return sourceAssetSubTypes;
	}

	public void setSourceAssetSubTypes(List<AssetSubTypeDTO> sourceAssetSubTypes) {
		this.sourceAssetSubTypes = sourceAssetSubTypes;
	}

	public List<AssetSubTypeDTO> getDestinationAssetSubTypes() {
		return destinationAssetSubTypes;
	}

	public void setDestinationAssetSubTypes(
			List<AssetSubTypeDTO> destinationAssetSubTypes) {
		this.destinationAssetSubTypes = destinationAssetSubTypes;
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

}
