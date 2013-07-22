package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import bcit.ca.infosys.KeyboardCowboys.exceptions.EmployeeAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.exceptions.PrincipleAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PayLevelAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PrincipleAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PrincipleRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetAllPayLevels;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetSupervisors;

/**
 * @author Andrej Koudlai
 * 
 *         Employee Controller Class that is responsible for instantiating a new
 *         employee and principal and allowing its attributes to be filled. The
 *         employee is then persisted into the database. This is accomplish by
 *         the employeeRegistration bean and the supervisors are retrieved from
 *         the employeeAccess bean. This is a Stateful session bean which is
 *         ConversationScoped
 */
@Named("EmployeeController")
@ConversationScoped
@Local(EmployeeControllerInterface.class)
@Stateful
public class EmployeeController implements Serializable,
        EmployeeControllerInterface {
    /**
     * Default serializable ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Injected log used to log that status of the bean.
     */
    @Inject
    private Logger log;
    /**
     * Injecting pay level for an employee controller
     */
    @Inject
    private PayLevelAccessInterface payLevelAccess;
    /**
     * Injection of employee registration access bean that persists new employee
     * into db.
     */
    @Inject
    private EmployeeRegistrationInterface employeeRegistration;
    /**
     * Injection of principle registration access bean that persists new
     * principle into db.
     */
    @Inject
    private PrincipleRegistrationInterface princRegistration;

    @Inject 
    private PrincipleAccessInterface principleAccess;
    /**
     * Injection of the conversation for the conversation scoped bean.
     */
    @Inject
    private Conversation conversation;

    /**
     * Injection of the Stateless session bean with is responsible for
     * retrieving information from the database regarding employees.
     */
    @Inject
    private EmployeeAccessInterface employeeAccess;

    /**
     * Principle object with a empID and hashed password attribute.
     */
    private Principle principle;

    /**
     * Temporary place holder for the new employee that is being created.
     */
    private Employee employee;

    private Employee selectedEmployee;
    /**
     * Temporary place holder for all the supervisors. See getSuperVisors().
     */
    private List<Employee> superVisors;

    /**
     * Temporary place holder for all the employees. See displayEmployees().
     */
    private List<Employee> employees;
    private List<Employee> filteredEmployees;
    private Boolean submitted = false;
    /**
     * Flag set while updating Employee, reset to false when updateEmployee()
     * returns.
     */
    private boolean editing;

    private List<PayLevel> payLevels;

    /**
     * Default constructor that creates an employee and principle.
     */
    public EmployeeController() {
        principle = new Principle();
        employee = new Employee();
        employee.setEmpPayInfo(new EmployeePayInfo());
    }

    /**
     * Save employee calls employee and principle Access bean persist methods to
     * persist both dependent objects into db. It uses user transaction state to
     * allow rollback in case if persist failed. Message will be displayed with
     * either success or failed exception.
     * 
     * @return null (refresh page)
     */
    public String saveEmployee() {
        FacesMessage msg = new FacesMessage();
        employee.getEmpPayInfo().setEpiEmployee(employee);
        try {
            employeeRegistration.register(employee);
            principle.setEmp(employee);
            princRegistration.register(principle);
            log.info("New employee saved @ EmployeeController.class");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            msg.setSummary("Employee added successfully");
        } catch (PrincipleAlreadyExistsException e) {
            log.info("Employee Registration failed @ EmployeeController.class");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary(e.getMessage());
            FacesContext.getCurrentInstance().addMessage("unique", msg);
            return null;
        } catch (EmployeeAlreadyExistsException e) {
            log.info("Employee Registration failed @ EmployeeController.class");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary(e.getMessage());
            FacesContext.getCurrentInstance().addMessage("unique", msg);
            return null;
        }

        FacesContext.getCurrentInstance().addMessage("unique", msg);
        submitted = true;
        conversation.end();
        log.info("Conversation ended @ EmployeeController.class");
        return null;
    }

    /**
     * Returns the new employee that is being created.
     * 
     * @return new employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns the principle for the employee that is being created.
     * 
     * @return new principle
     */
    public Principle getPrinciple() {
        return principle;
    }

    /**
     * Returns a list of all the supervisors.
     * 
     * @return list of all the supervisors
     */
    @Produces
    @GetSupervisors
    public List<Employee> getSuperVisors() {
        return superVisors;
    }

    /**
     * Starts the CDI conversation if it has not been started.
     */
    public void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            log.info("Conversation started @ EmployeeController.class");
            superVisors = employeeAccess.getAllSuperVisors();
        }
        if (payLevels == null) {
            payLevels = payLevelAccess.getAllPayLevels();
        }
    }

    /**
     * Checks if the user being created is a supervisor or human resources.
     * 
     * @return true if hr or supervisor, otherwise false
     */
    public boolean isHRorSuper() {
        if (employee.getEmpRole() != null
                && (employee.getEmpRole().equals("SUPERVISOR") || employee
                        .getEmpRole().equals("HR"))) {
            return true;
        }
        return false;
    }

    /**
     * Loads all existing employees.
     */
    public void displayEmployees() {
        startConversation();
        if (employees == null) {
            employees = employeeAccess.getAllEmployees();
        }
    }

    /**
     * 
     * @return all loaded employees.
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * 
     * @return value of editing.
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * Setter for editing flag.
     * 
     * @param editing
     *            new value for editing flag.
     */
    public void setEditing(final boolean editing) {
        this.editing = editing;
        RequestContext.getCurrentInstance().reset("viewEmployees"); 
    }

    /**
     * Setter for temporary employee placeholder.
     * 
     * @param emp
     *            Employee to place in controller's placeholder.
     */
    public void setEmployee(final Employee emp) {
        employee = emp;
    }

    /**
     * Updates specified employee.
     * 
     * @return Empty, just to refresh view.
     */
    public String updateEmployee() {
        employeeRegistration.updateEmployee(employee);
        princRegistration.merge(principle);
        log.info("Updateing Employee");
        editing = false;
        return "";
    }

    /**
     * Retrieves list of all pay levels
     * 
     * @return payLevels
     */
    @Produces
    @GetAllPayLevels
    public List<PayLevel> getPayLevels() {
        return payLevels;
    }

    /**
     * Retrieve list of filtered employees
     * 
     * @return filteredEmployees
     */
    @Override
    public List<Employee> getFilteredEmployees() {
        return filteredEmployees;
    }

    /**
     * Set filtered employees
     */
    @Override
    public void setFilteredEmployees(List<Employee> filteredEmployees) {
        this.filteredEmployees = filteredEmployees;
    }

    /**
     * Returns true if submit succeeded.
     * 
     * @return submitted
     */
    @Override
    public Boolean getSubmitted() {
        return submitted;
    }

    @Override
	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

    @Override
	public void setSelectedEmployee(Employee selectedEmployee) {
		employee = selectedEmployee;
		principle = principleAccess.getPrincipalByEmp(selectedEmployee);
	}
}
