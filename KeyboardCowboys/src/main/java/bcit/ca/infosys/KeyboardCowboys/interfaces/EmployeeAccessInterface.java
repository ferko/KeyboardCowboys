package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;



import bcit.ca.infosys.KeyboardCowboys.model.Employee;
/**
 * Interface for Employee Access class
 * 
 * @author Andrej Koudlai
 *
 */

public interface EmployeeAccessInterface {
	

	public Employee getEmployeeByUserName(String userName);
    public List<Employee> getAllEmployees();
    public List<Employee> getAllSuperVisors();
	public boolean isApprover(Employee emp);
	public List<Employee> getUnassignedEmployees(Employee currentEmployee);
}
