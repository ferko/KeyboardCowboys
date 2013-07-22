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
 *         PasswordValidator validates that the password is at least 6
 *         characters long with at least 1 upper case characters, at least 1
 *         lower case character, at least 1 numeric character, and at least 1
 *         special character.
 *         
 *         20 character maximum limit?
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String VALID_PASSWORD_REGEX = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{6,20})";

    public PasswordValidator() {
        pattern = Pattern.compile(VALID_PASSWORD_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String password = value.toString();
        matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                    "Password must be 6 - 20 characters long,"
                            + "\nmust contain at least one upper case characters,"
                            + "\nmust contain at least one lower case character,"
                            + "\nmust contain at least one numeric character,"
                            + "\nmust contain at least one special character.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
