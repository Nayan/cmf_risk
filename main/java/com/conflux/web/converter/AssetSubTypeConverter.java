package com.conflux.web.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.conflux.service.IAssetTypeService;
import com.conflux.service.dto.AssetSubTypeDTO;

@ManagedBean
@RequestScoped
public class AssetSubTypeConverter implements Converter {

	@ManagedProperty(value = "#{assetTypeService}")
	private IAssetTypeService assetTypeService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return assetTypeService.getAssetSubTypeById(value);

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		AssetSubTypeDTO assetSubTypeDTO = (AssetSubTypeDTO) value;
		Integer assetSubTypeId = assetSubTypeDTO.getAssetSubTypeId();
		return assetSubTypeId.toString();
	}

	public IAssetTypeService getAssetTypeService() {
		return assetTypeService;
	}

	public void setAssetTypeService(IAssetTypeService assetTypeService) {
		this.assetTypeService = assetTypeService;
	}

}
