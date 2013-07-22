package bcit.ca.infosys.KeyboardCowboys.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetSRWorkPackages;

@Named("srWPconverter")
public class StatuesReportWPConverter implements Converter{

	@Inject
	@GetSRWorkPackages
	private List<WorkPackage> workPackages;
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {

		if (submittedValue.trim().equals("") || submittedValue == null) {
			return null;
		} else {
			for (WorkPackage item : workPackages) {
				
				if ((item.getDbID().toString()).equals(submittedValue)) {
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
			return ((WorkPackage) value).getDbID().toString();
		}
	}
}
