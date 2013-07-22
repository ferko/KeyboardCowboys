package bcit.ca.infosys.KeyboardCowboys.interfaces;

import bcit.ca.infosys.KeyboardCowboys.exceptions.EmployeeAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
/**
 * Interface for EmployeeRegistration class.
 * 
 * @author Andrej
 *
 */

public interface EmployeeRegistrationInterface {

    public void updateEmployee(Employee emp);
    public void register(Employee emp) throws EmployeeAlreadyExistsException;
}
