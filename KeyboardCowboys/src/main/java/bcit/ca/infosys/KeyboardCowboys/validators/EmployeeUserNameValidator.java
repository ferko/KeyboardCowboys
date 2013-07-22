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
 *         EmployeUserNameValidator validates that the Employee User Name is
 *         between 3 to 15 characters long and only contains letters, numbers,
 *         hyphens, and underscores.
 */
@FacesValidator("employeeUserNameValidator")
public class EmployeeUserNameValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,15}$";

    public EmployeeUserNameValidator() {
        pattern = Pattern.compile(VALID_USERNAME_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String employeeUserName = value.toString();
        matcher = pattern.matcher(employeeUserName);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Employee User Name must be three to fifteen characters long and cannot contain special characters");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}