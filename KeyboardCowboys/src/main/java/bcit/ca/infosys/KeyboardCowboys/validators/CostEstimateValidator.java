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
 *         CostEstimateValidator validates that the cost has up to 2 decimal
 *         places
 */
@FacesValidator("costEstimateValidator")
public class CostEstimateValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_COST_ESTIMATE_REGEX = "[0-9]+(\\.[0-9][0-9]?)?";

    public CostEstimateValidator() {
        pattern = Pattern.compile(VALID_COST_ESTIMATE_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String CostEstimate = value.toString();
        matcher = pattern.matcher(CostEstimate);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("Invalid cost estimate.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
