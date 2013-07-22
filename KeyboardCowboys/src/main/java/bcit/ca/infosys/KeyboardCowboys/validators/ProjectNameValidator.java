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
 *         ProjectNameValidator validates that the Project Name is between 3 to
 *         20 characters long
 */
@FacesValidator("projectNameValidator")
public class ProjectNameValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_PROJECT_NAME_REGEX = "^.{3,20}$";

    public ProjectNameValidator() {
        pattern = Pattern.compile(VALID_PROJECT_NAME_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String projectName = value.toString();
        matcher = pattern.matcher(projectName);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Project Name must be between three to twenty characters long");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}