package bcit.ca.infosys.KeyboardCowboys.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Nem
 * 
 *         WorkPackageEndDateValidator validates that the WorkPackage End Date is in the
 *         correct format and is after the Start Date
 */
@FacesValidator("workPackageEndDateValidator")
public class WorkPackageEndDateValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_DATE_REGEX = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(\\d\\d)";

    public WorkPackageEndDateValidator() {
        pattern = Pattern.compile(VALID_DATE_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String workPackageEndDate = value.toString();
        matcher = pattern.matcher(workPackageEndDate);

        UIInput startDateComponent = (UIInput) component.getAttributes().get(
                "startDateComponent");

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Invalid End Date format. Ex. mm/dd/yy");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        try {
            Date startDate = df.parse(startDateComponent.toString());
            Date endDate = df.parse(workPackageEndDate);

            if (endDate.before(startDate)) {
                FacesMessage msg = new FacesMessage(
                        "End Date cannot be before Start Date");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}