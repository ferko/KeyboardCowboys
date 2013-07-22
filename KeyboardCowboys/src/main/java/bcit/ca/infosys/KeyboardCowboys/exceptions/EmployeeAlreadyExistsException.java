/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.exceptions;

/**
 * @author Andrej
 *
 */
public class EmployeeAlreadyExistsException extends Exception{        
    public EmployeeAlreadyExistsException(String message)
    {
        super(message);
    }
}
