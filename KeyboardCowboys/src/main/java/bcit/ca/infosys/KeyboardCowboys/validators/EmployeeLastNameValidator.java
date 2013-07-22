package bcit.ca.infosys.KeyboardCowboys.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Nem
 * 
 *         EmployeNameValidator validates that the Employee last name is between 3 to
 *         20 characters and can only contain alphabetic characters and space
 */
@FacesValidator("employeeLastNameValidator")
public class EmployeeLastNameValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_NAME_REGEX = "^[a-zA-Z '-]{3,20}+$";

    public EmployeeLastNameValidator() {
        pattern = Pattern.compile(VALID_NAME_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String employeeName = value.toString();
        matcher = pattern.matcher(employeeName);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Employee Last Name must be between three to twenty characters long "
                            + "and can only contain alphabetic characters");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
