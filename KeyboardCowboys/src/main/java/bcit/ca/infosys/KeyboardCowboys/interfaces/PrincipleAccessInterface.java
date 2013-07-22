package bcit.ca.infosys.KeyboardCowboys.interfaces;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;


/**
 * Interface for Principle Access class.
 * @author Andrej
 *
 */
public interface PrincipleAccessInterface {
    public String getHSHPwd(String empID);

	Principle getPrincipalByEmp(Employee emp);
}
