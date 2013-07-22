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
 *         WorkPackageIDValidator validates that the WorkPackage ID is between 3 to 6
 *         characters long and only contains letters and numbers
 */
@FacesValidator("workPackageIDValidator")
public class WorkPackageIDValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String workPackageID = value.toString();
        
        if(workPackageID.length() != 1 && workPackageID.charAt(0) == '0'){
            FacesMessage msg = new FacesMessage(
                    "WorkPackage ID must a single character, not \"0\". ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }


    }
}