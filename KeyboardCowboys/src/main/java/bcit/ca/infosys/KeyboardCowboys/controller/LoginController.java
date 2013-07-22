package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.LoginControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

/**
 * In charge of authenticating the employee which is trying to log onto the
 * system. After the employee is authenticated his credentials are stored in the
 * session scoped bean so that the information can be inherited by other beans
 * within the application and also logs out the employee from the system
 * 
 * @author Jitin Dhillon
 * 
 */
@Named("login")
@SessionScoped
@Local(LoginControllerInterface.class)
@Stateful
public class LoginController implements Serializable, LoginControllerInterface {

	/**
	 * Default serializable ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Injected logger to log method calls.
	 */
	@Inject
	private Logger log;
	/**
	 * Injection point for the employeeAccess which is used to query the
	 * database for the employee attempting to log onto the application.
	 */
	@Inject
	private EmployeeAccessInterface employeeAccess;

	/**
	 * Username that the employee is attempting to authenticate with.
	 */
	private String userName;

	/**
	 * Password the employee has used to try to authenticate.
	 */
	private String password;

	@Inject
	private TimeSheetAccessInterface timeSheetAccess;
	/**
	 * Contains the information for the employee that is log into the system.
	 */
	private Employee currentEmployee;
	private List<TimeSheet> rejectedTimeSheets;
	/**
	 * Servlet which is used to programmatically authenticate the employee.
	 */
	private HttpServletRequest request;
	
	private boolean approver;
	
	private String fullName;
	
	private String role;

	/**
	 * Tries to authenticate the employee with the username and password that
	 * they applied and if sucessful then the user is retrieved fromt he
	 * database and store into the currentEmployee variable.
	 * 
	 * @return navigation case which is either 'welcome' or 'null'
	 */
	public String login() {
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		request = (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		try {
			request.login(userName, SHAHash.hash(password));
			currentEmployee = employeeAccess.getEmployeeByUserName(userName);
			approver = employeeAccess.isApprover(currentEmployee);
			fullName = currentEmployee.getEmpFName() + ' ' + currentEmployee.getEmpLName();
			role = currentEmployee.getEmpRole();
			
		     /*Inactive Employee*/
	        if(currentEmployee.getEmpRole().equals("")){
	            msg.setSummary("Incorrect Username/Password");
	            FacesContext.getCurrentInstance().addMessage("login_form", msg);
	            log.info("Inactive Employee login attempt @ LoginController.class");
	            logOut();
	            return "";
	        }
			log.info("Employee logged in successfully @ LoginController.class");
			
		} catch (ServletException e) {
			msg.setSummary("Incorrect Username/Password");
			FacesContext.getCurrentInstance().addMessage("login_form", msg);
			log.info("Unsuccessful login attempt @ LoginController.class");
			return "";
		}
		
		rejectedTimeSheets = timeSheetAccess.getRejectedTimeSheets(currentEmployee);
		password = "";
		return "welcome";
	}

	/**
	 * Logs the employee out of the system. ReAuthentication is required if the
	 * user wishes the continue with the application.
	 * 
	 * @return navigation case which redirects back to the login form
	 */
	public String logOut() {
		rejectedTimeSheets = null;
		currentEmployee = null;
		userName = null;
		FacesContext.getCurrentInstance().getExternalContext()
		.invalidateSession();
		log.info("Employee logged out @ LoginController.class");
		return "logout";
	}

	/**
	 * Retrieves the request the employee has made to authenticate which is used
	 * to try and authenticate and to also display an error msg.
	 * 
	 * @return HttpServletRequest object
	 */
	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	/**
	 * Triggered by a preRenderView event on the login form screen, which then
	 * redirects the employee to the welcome page if they are already logged in.
	 * 
	 * @param event
	 *            the event which triggered the method
	 */
	public void loggedIn(final ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();
		if (currentEmployee != null) {
			ConfigurableNavigationHandler nav
			        = (ConfigurableNavigationHandler) fc
					.getApplication().getNavigationHandler();

			nav.performNavigation("welcome");
		}

	}

	/**
	 * Returns the userName that the employee is attempting to
	 * authenticate with.
	 * 
	 * @return authentication userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the userName that the employee is authenticating with.
	 * 
	 * @param userName
	 *            authentication userName
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * Sets the password that the employee is using to authenticate.
	 * 
	 * @param password
	 *            authentication password
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Returns the password used to authenticate the employee attemping to log
	 * in.
	 * 
	 * @return authentication password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the currentEmployee that is authenticated onto the system. This
	 * is a Producer method with can be injected using the @CurrentEmployee
	 * annotation.
	 * 
	 * @return logged in employee
	 */
	@Produces
	@CurrentEmployee
	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	/**
     * Returns whether the current employee is or is not a supervisor. This
     * is a Producer method with can be injected using the @CurrentEmployee
     * annotation.
     * 
     * @return boolean, the current employee is or is not a supervisor
	 */

	public boolean isSuperVisor() {
		return currentEmployee.getEmpRole().equals("SUPERVISOR");
	}
	
    /**
     * Returns whether the current employee is or is not an HR. This
     * is a Producer method with can be injected using the @CurrentEmployee
     * annotation.
     * 
     * @return boolean, the current employee is or is not an HR
     */

	public boolean isHR() {
		return currentEmployee.getEmpRole().equals("HR");
	}
	
	/**
	 * Checks if an employee is an engineer by verifying if there are any engineered packages.
	 * 
	 * @return true if not empty, false if empty
	 */
	public boolean isEngineer() {
		return !currentEmployee.getWpEngineered().isEmpty();
	}

	/**
	 * Verifies if an employee is a manager by checking if he has any managed projects. 
	 * 
	 * @return true if not empty, false if empty
	 */
    @Override
	public boolean isManager() {
		return !currentEmployee.getManagedProjects().isEmpty();
	}
    
    /**
     * Verifies if an employee is an approver
     * 
     * @return true or false
     */
	@Override
	public boolean isApprover()
	{
		return approver;
	}
	
	/**
	 * Getter for the full name of an employee
	 * 
	 * @return Full Name
	 */
   public String getFullName() {
        return fullName;
    }

   /**
    * Setter for employee full name
    */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for employee role
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public List<TimeSheet> getRejectedTimeSheets()
    {
    	return rejectedTimeSheets;
    }
}
