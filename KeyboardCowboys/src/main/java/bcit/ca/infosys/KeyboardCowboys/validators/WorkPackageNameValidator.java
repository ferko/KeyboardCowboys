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
 *         WorkPackageNameValidator validates that the WorkPackage Name is between 3 to
 *         20 characters long
 */
@FacesValidator("workPackageNameValidator")
public class WorkPackageNameValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_WorkPackage_NAME_REGEX = "^.{3,20}$";

    public WorkPackageNameValidator() {
        pattern = Pattern.compile(VALID_WorkPackage_NAME_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String workPackageName = value.toString();
        matcher = pattern.matcher(workPackageName);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "WorkPackage Name must be between three to twenty characters long");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}