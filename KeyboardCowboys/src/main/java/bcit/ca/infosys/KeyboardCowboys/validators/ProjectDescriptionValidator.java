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
 *         ProjectDescriptionValidator validates that the Project Name is at least 5 characters long
 */
@FacesValidator("projectDescriptionValidator")
public class ProjectDescriptionValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_PROJECT_DESCRIPTION_REGEX = "^.{5,1000}$";

    public ProjectDescriptionValidator() {
        pattern = Pattern.compile(VALID_PROJECT_DESCRIPTION_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String projectDescription = value.toString();
        matcher = pattern.matcher(projectDescription);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Project Description must be at least 5 characters long.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
