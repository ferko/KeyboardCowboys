package bcit.ca.infosys.KeyboardCowboys.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

@FacesConverter("workPackageIDConverter")
public class WorkPackageIDConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        System.out.println(value.toString().length());
        if(value.toString().length() != 1 || value.toString().charAt(0) == '0'){
            FacesMessage msg = new FacesMessage(
                    "Must be a single character, not \"0\". ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        
        System.out.println("Beginning");

        if (value == null || (value.trim().length() == 0)) {
            return value;
        }

        System.out.println("value passed");

        String parentID = ((WorkPackage) component.getAttributes().get("wpIDAttribute")).getWpID();

        System.out.println("Got parent ID: " + parentID);

        String workPackageID = null;

        if (parentID != null) {
            for (int i = 0; i < parentID.length(); i++) {
                if (parentID.charAt(i) != '0') {
                    workPackageID += parentID.charAt(i);
                }
            }
        }

        System.out.println("WorkPackageID created from Parent: "
                + workPackageID);
        
        
        if(workPackageID != null){
        workPackageID += value.toString();
        }
        else {
            workPackageID = value.toString();
        }

        System.out.println("WorkPackageID value added: " + workPackageID);

        for (int j = workPackageID.length(); j < 6; j++) {
            workPackageID += '0';
        }

        System.out.println("WorkPackageID 0s appended: " + workPackageID);

        System.out.println("End");

        return workPackageID;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        return value.toString();
    }
}
