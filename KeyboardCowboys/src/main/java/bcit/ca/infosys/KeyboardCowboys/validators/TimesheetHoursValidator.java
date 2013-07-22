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
 *         CostEstimateValidator validates that the hours are in increments of 30min (0.5) and that
 *         the hours cannot exceed 24h
 */
@FacesValidator("timesheetHoursValidator")
public class TimesheetHoursValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_HOURS_REGEX = "^([0-9]|1[0-9]|2[0-4])+(\\.5?)?";

    public TimesheetHoursValidator() {
        pattern = Pattern.compile(VALID_HOURS_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String hours = value.toString();
        matcher = pattern.matcher(hours);
        
      //  !matcher.matches() ||
        if ( Double.parseDouble(hours) > 24 || Double.parseDouble(hours) < 0 ) {
            FacesMessage msg = new FacesMessage(
                    "Invalid hours");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
