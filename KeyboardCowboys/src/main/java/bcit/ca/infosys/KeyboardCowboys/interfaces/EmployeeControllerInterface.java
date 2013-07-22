/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;

/**
 * @author Andrej Koudlai
 * 
 *         Employee Controller Class that is responsible for instantiating a new
 *         employee, and calling principle and employee access beans to persist
 *         all to the db.
 */
public interface EmployeeControllerInterface {

    /**
     * Save employee calls employee and principle Access bean persist methods to
     * persist both dependent object into db. It uses user transaction state to
     * allow rollback in case if persist failed. Message will be displayed with
     * either success or failed exception.
     * 
     * @return null (refresh page)
     */
    public String saveEmployee();

    /**
     * getter for an employee
     * 
     * @return this employee
     */
    public Employee getEmployee();

    /**
     * Starts the conversation for the Conversation scoped bean
     */
    public void startConversation();
    
    public List<Employee> getSuperVisors();
    /**
     * Checks if the employee is a supervisor or human resources
     * @return true if hr or supervisor, otherwise false
     */
    public boolean isHRorSuper();
    
    /**
     * getter for principle
     * 
     * @return this principle
     */
    public Principle getPrinciple();

    public List<Employee> getEmployees();
    public boolean isEditing();
    public void setEditing(boolean editing);
    public void displayEmployees();
    public void setEmployee(Employee emp);
    public String updateEmployee();
	public List<PayLevel> getPayLevels();

	List<Employee> getFilteredEmployees();

	void setFilteredEmployees(List<Employee> filteredEmployees);

	Boolean getSubmitted();

	Employee getSelectedEmployee();

	void setSelectedEmployee(Employee selectedEmployee);
    
}
