package bcit.ca.infosys.KeyboardCowboys.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetAllPayLevels;
@Named("PayLevelConverter")
public class PayLevelConverter implements Converter{
	@Inject
	@GetAllPayLevels
	private List<PayLevel> payLevels;

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {

		if (submittedValue.trim().equals("") || submittedValue == null) {
			return null;
		} else {
			for (PayLevel payLevel : payLevels) {
				if (payLevel.getPlLevel().equals(submittedValue)) {
					return payLevel;
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
			return (((PayLevel) value).getPlLevel());
		}
	}
}
