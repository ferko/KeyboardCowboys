package bcit.ca.infosys.KeyboardCowboys.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetUnassignedEmployees;

@Named("SupervisorEmployeeConverter")
public class SupervisorEmployeeConverter implements Converter{
    @Inject
    @GetUnassignedEmployees
    private List<Employee> employees;

    public Object getAsObject(FacesContext facesContext, UIComponent component,
            String submittedValue) {

        if (submittedValue.trim().equals("") || submittedValue == null) {
            return null;
        } else {
            for (Employee employee : employees) {
                if (Integer.toString(employee.getEmpID()).equals(submittedValue)) {
                    return employee;
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
