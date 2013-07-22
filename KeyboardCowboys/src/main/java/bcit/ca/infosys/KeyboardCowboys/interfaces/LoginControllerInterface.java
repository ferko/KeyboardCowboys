package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import javax.faces.event.ComponentSystemEvent;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;

/**
 * Interface for login controller
 * 
 * @author Andrej Koudlai
 * 
 */
public interface LoginControllerInterface {

	/**
	 * Indicates whether the currentEmployee is HR
	 * @return true if HR
	 */
	public boolean isHR();
	
	/**
	 * Indicates whether the currentEmployee is a supervisor
	 * @return true if supervisor
	 */
	public boolean isSuperVisor();
	/**
	 * Logs in the employee
	 * 
	 * @return Navigation case
	 */
	public String login();

	/**
	 * Logs out the current user
	 * 
	 * @return Navigation case
	 */
	public String logOut();

	/**
	 * Checks if the user is logged in
	 * 
	 * @param event
	 */
	public void loggedIn(ComponentSystemEvent event);

	/**
	 * Gets the userName the employee is authenticating with
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * Stores the userName of the employee who is authenticating
	 * 
	 * @param userName
	 *            Authernication username
	 */
	public void setUserName(String userName);

	/**
	 * Gets the password the employee attempted to log on with
	 * 
	 * @return
	 */
	public String getPassword();

	/**
	 * Stores the password the employee is authenticating with
	 * 
	 * @param password
	 *            Authentication password
	 */
	public void setPassword(String password);

	/**
	 * gets the Employee that is currently logged in
	 * 
	 * @return logged in employee
	 */
	public Employee getCurrentEmployee();

	public boolean isManager();

	public boolean isApprover();
	
	public boolean isEngineer();
	
	public String getFullName();
	public void setFullName(String fullName);
    public String getRole();
    public void setRole(String role);
    public List<TimeSheet> getRejectedTimeSheets();
}
