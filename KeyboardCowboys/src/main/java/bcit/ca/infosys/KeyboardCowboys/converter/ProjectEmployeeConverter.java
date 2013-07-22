package bcit.ca.infosys.KeyboardCowboys.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetProjectEmployees;

@Named("CreateProjConverter")
public class ProjectEmployeeConverter implements Converter {
	@Inject
	@GetProjectEmployees
	private Project project;

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {

		if (submittedValue.trim().equals("") || submittedValue == null) {
			return null;
		} else {
			for (Employee item : project.getProjEmployeesList()) {
				
				if (item.getEmpID() == Integer.parseInt(submittedValue)) {
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
			return Integer.toString(((Employee) value).getEmpID());
		}
	}
}
