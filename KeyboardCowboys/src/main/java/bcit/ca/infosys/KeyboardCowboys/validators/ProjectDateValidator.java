package bcit.ca.infosys.KeyboardCowboys.validators;

import java.util.Date;
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
 *         ProjectDateValidator validates that the Project End Date is in the
 *         correct format and that it does not come before the Project Start
 *         Date
 */
@FacesValidator("projectDateValidator")
public class ProjectDateValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_START_DATE_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public ProjectDateValidator() {
        pattern = Pattern.compile(VALID_START_DATE_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value == null || component.getAttributes().get("startDate") == null)
            return;

        // String projectEndDate = value.toString();
        // matcher = pattern.matcher(projectEndDate);
        //
        // if (!matcher.matches()) {
        // FacesMessage msg = new FacesMessage(
        // "Invalid End Date format. Ex. dd/mm/yyyy");
        // msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        // throw new ValidatorException(msg);
        //
        // }

        Date endDate = (Date) value;
        Date startDate = (Date) component.getAttributes().get("startDate");

        if (endDate.before(startDate)) {
            FacesMessage msg = new FacesMessage(
                    "Start Date cannot be after End Date");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
