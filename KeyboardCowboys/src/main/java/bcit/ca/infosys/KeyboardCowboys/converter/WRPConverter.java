package bcit.ca.infosys.KeyboardCowboys.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.WRProjects;

@Named("WRPConverter")
public class WRPConverter implements Converter {

	@Inject
	@WRProjects
	private List<Project> projects;

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {

		if (submittedValue.trim().equals("") || submittedValue == null) {
			return null;
		} else {
			for (Project item : projects) {
				
				if (item.getProjID().equals(submittedValue)) {
					return item;
				}
			}
		}
		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return ((Project) value).getProjID();
		}
	}
}
