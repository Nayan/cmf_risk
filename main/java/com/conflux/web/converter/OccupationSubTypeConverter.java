package com.conflux.web.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.conflux.service.IOccupationTypeService;
import com.conflux.service.dto.OccupationSubTypeDTO;

@ManagedBean
@RequestScoped
public class OccupationSubTypeConverter implements Converter {

	@ManagedProperty(value = "#{occupationTypeService}")
	private IOccupationTypeService occupationTypeService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return occupationTypeService.getOccupationSubTypeById(value);

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		OccupationSubTypeDTO occupationSubTypeDTO = (OccupationSubTypeDTO) value;
		Integer occupationSubTypeId = occupationSubTypeDTO
				.getOccupationSubTypeId();
		return occupationSubTypeId.toString();
	}

	public IOccupationTypeService getOccupationTypeService() {
		return occupationTypeService;
	}

	public void setOccupationTypeService(
			IOccupationTypeService occupationTypeService) {
		this.occupationTypeService = occupationTypeService;
	}

}
