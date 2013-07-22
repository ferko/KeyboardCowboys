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
 *         WorkPackageStartDateValidator validates that the WorkPackage Start Date is in the correct format
 */
@FacesValidator("projectStartDateValidator")
public class WorkPackageStartDateValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_START_DATE_REGEX = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(\\d\\d)";

    public WorkPackageStartDateValidator() {
        pattern = Pattern.compile(VALID_START_DATE_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String projectStartDate = value.toString();
        matcher = pattern.matcher(projectStartDate);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Invalid Start Date format. Ex. mm/dd/yy");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}