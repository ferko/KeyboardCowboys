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
 *         ProjectIDValidator validates that the Project ID is between 3 to 6
 *         characters long and only contains numbers
 */
@FacesValidator("projectIDValidator")
public class ProjectIDValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_PROJECT_ID_REGEX = "^[0-9]{3,6}$";

    public ProjectIDValidator() {
        pattern = Pattern.compile(VALID_PROJECT_ID_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String projectID = value.toString();
        matcher = pattern.matcher(projectID);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Project ID must be between three to six characters long and can only contains numbers");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}