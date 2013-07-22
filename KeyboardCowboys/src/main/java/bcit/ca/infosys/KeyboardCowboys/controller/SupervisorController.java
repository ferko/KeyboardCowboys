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

import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.SupervisorControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetAllProjects;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetUnassignedEmployees;

/**
 * This class is responsible for assigning employees to a project based on who their supervisor is
 * 
 * @author Frank Berenyi
 *
 */
@Named("SupervisorController")
@ConversationScoped
@Local(SupervisorControllerInterface.class)
@Stateful
public class SupervisorController implements Serializable, SupervisorControllerInterface {
    /**
	 * Defualt serializable id
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger which is used to log the methods being called in this bean
	 */
	@Inject
	private Logger log;
	/**
	 * Injection of the conversation so that it can be started and stoped in the bean
	 */
	@Inject
    private Conversation conversation;
    
	/**
	 * Inject point for the employee access which is used to retrieve employees
	 * from the database
	 */
	@Inject
    private EmployeeAccessInterface employeeAccess;
	
	/**
	 * Injection point for the projectAccess which is used to retrieve projects
	 * from the database
	 */
    @Inject
    private ProjectAccessInterface projectAccess;
    
    /**
     * Injection point for the project registration which is used to insert and update
     * projects in the database
     */
    @Inject
    private ProjectRegistrationInterface projectRegistration;
    
    @Inject
    @CurrentEmployee
    private Employee currentEmployee;
    /**
     * List of all the projects
     */
    private List<Project> projects;
    /**
     * Unnassigned Employee list.
     */
    private List<Employee> employees;
    private Boolean submitted = false;
    /**
     * Employee that is being added to the project
     */
    private Employee employee;

    /**
     * Project that the employee is being assigned to
     */
    private Project project;

    /**
     * Gets all employees that have not been assigned to a project
     * from the employeeAccess class.
     */
    public void getUnassignedEmployees() {
        log.info("Getting unassigned Employees @ SupervisorController.class");
    	employees = employeeAccess.getUnassignedEmployees(currentEmployee);
    }

    /**
     * Retrieves all the projects from the database by using
     * the projectAccess
     * @return All the projects
     */
    public List<Project> getAllProjects() {
        if (projects == null) {
            log.info("Getting all Projects @ SupervisorController.class");
            projects = projectAccess.getAllProjects();
        }
        return projects;
    }

    /**
     * If the conversation is not started then conversation gets started
     * and the projects and employees attributes get initialized
     */
    public void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            log.info("Conversation Started @ SupervisorController.class");
            getAllProjects();
            getUnassignedEmployees();
        }
    }

    /**
     * Ends conversation if it has been started.
     */
    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            log.info("Conversation ended @ SupervisorController.class");

        }
    }

    /**
     * this method adds the employee to the project and then updates the
     * project and then displays a faces message.
     */
    public void saveEmployee() {
    	FacesMessage msg = new FacesMessage();
    	msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Successfully add " + employee.getEmpFName()
				+ " " + employee.getEmpLName()  
						+ " to Project: " + project.getProjName());
        project.getProjEmployees().add(employee);
		FacesContext.getCurrentInstance().addMessage("formAssignEmp", msg);
        log.info("Updateing Project @ SupervisorController.class");
		projectRegistration.update(project);
		submitted = true;
		endConversation();
    }

    /**
     * Returns all the employess which are not assigned to a project.
     * This class is also marked @Produces so that it can be injected
     * into a converter
     * 
     * @return all unassigned employsees
     */
    @Produces
    @GetUnassignedEmployees
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Returns all projects and is annotated with @Produces so that
     * it can be injected into a converter
     * 
     * @return all projects
     */
    @Produces
    @GetAllProjects
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Returns the employee that is being assigned to a project
     * @return unassigned employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the unassigned employee that is being assign to a project
     * @param employee Unassigned employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Gets the selected project to assign an employee to
     * @return selected project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project that the unassigned employee will be added to
     * @param project Project to assign employees to
     */
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public Boolean getSubmitted()
    {
    	return submitted;
    }
}
