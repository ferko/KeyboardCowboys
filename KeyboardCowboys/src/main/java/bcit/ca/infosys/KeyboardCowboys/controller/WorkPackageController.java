/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.DragDropEvent;

import bcit.ca.infosys.KeyboardCowboys.exceptions.WorkPackageAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PayLevelAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.AllProjects;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.NewWorkPackage;
import bcit.ca.infosys.KeyboardCowboys.util.EmployeeComparer;

/**
 * This class is used to create, view and edit workpackages
 * 
 * @author Andrej, Jitin
 * 
 */
@ConversationScoped
@Stateful
@Local(WorkPackageControllerInterface.class)
@Named("WorkPackageController")
public class WorkPackageController implements Serializable,
		WorkPackageControllerInterface {

	/**
	 * Defualt serializable ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger used to log which methods are being called
	 */
	@Inject
	private Logger log;

	/**
	 * Registration persistence class that uses em to operate entity.
	 */
	@Inject
	private WorkPackageRegistrationInterface wpRegistration;

	/**
	 * Injected conversation to control the lifetime of the bean
	 */
	@Inject
	private Conversation conversation;

	/**
	 * Injected projectAccess used to retrieve projects from the database
	 */
	@Inject
	private ProjectAccessInterface projectAccess;

	@Inject
	private PayLevelAccessInterface payLevelAccess;
	/**
	 * Injeced workPackageAccess use the retrieve workpackages from the database
	 */
	@Inject
	private WorkPackageAccessInterface workPackageAccess;

	/**
	 * Boolean used to indicate if the user is editing a work package
	 */
	private boolean editing = false;

	/**
	 * WotkPackage to be viewed and edited
	 */
	private WorkPackage selectedWorkPackage;

	/**
	 * WorkPackage that is to be created
	 */
	private WorkPackage workPackage;

	/**
	 * All the projects
	 */
	private List<Project> allProjects;
	
	private Boolean submitted = false;

	/**
	 * Default constructor of controller that initialises all fields.
	 */
	public WorkPackageController() {
		workPackage = new WorkPackage();
		workPackage.setWpEmployees(new ArrayList<Employee>());
	}

	@PostConstruct
	public void initWorkPackageBudget()
	{
		List<PayLevel> payLevels = payLevelAccess.getAllPayLevels();
		workPackage.setWpBudgets(new ArrayList<WorkPackageBudget>());
		for(PayLevel payLevel : payLevels)
		{
			WorkPackageBudget budget = new WorkPackageBudget();
			budget.setPayLevel(payLevel);
			budget.setWorkPackage(workPackage);
			workPackage.getWpBudgets().add(budget);
		}
	}
	/**
	 * Persists the newly created workpackage to the database by using the
	 * workPackageRegistation class.
	 * 
	 * @return Navigation Case
	 */
	public String saveWorkPackage() {
		String temp = "";
		if(workPackage.getWpPackage() != null)
		{
			for (int i = 0; i < workPackage.getWpPackage().getWpID().length(); i++) {
                if (workPackage.getWpPackage().getWpID().charAt(i) != '0') {
                    temp += workPackage.getWpPackage().getWpID().charAt(i);
                }
            }
		}
		temp += workPackage.getWpID();
		for (int j = temp.length(); j < 5; j++) {
            temp += '0';
        }
		workPackage.setWpID(temp);
		
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Successfully created Work Package.");
		try {
			log.info("Saving workpackage @ WorkPackageController.class");
			wpRegistration.register(workPackage);
		} catch (WorkPackageAlreadyExistsException e) {
			log.info("Failed to save workpackage @ WorkPackageController.class");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setSummary("Work Package already Exists!");
			FacesContext.getCurrentInstance().addMessage("formCreateWP", msg);
			e.printStackTrace();
			return "";
		}
		submitted = true;
		endConversation();
		FacesContext.getCurrentInstance().addMessage("formCreateWP", msg);
		return "null";
	}

	/**
	 * Merges the work Pacakge that has been editing by using the
	 * WorkPackageRegistration.class
	 * 
	 * @return Navigation Case
	 */
	public String mergeWorkPackage() {
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Successfully Updated Work Package.");
		log.info("Merging Work Package @ workpackageController.class");
		wpRegistration.mergeWorkPackage(workPackage);
		endConversation();
		FacesContext.getCurrentInstance().addMessage("unique", msg);
		editing = false;
		return "";
	}

	// No longer being used

	// /**
	// * @author Andrej Used to update an existing work package contents in
	// entity
	// * upon persist
	// * @param wp
	// * @return
	// */
	// public String mergeWorkPackage(WorkPackage wp) {
	// FacesMessage msg = new FacesMessage();
	// try {
	// wpRegistration.mergeWorkPackage(wp);
	// } catch (IllegalArgumentException e) {
	// msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	// msg.setSummary("Illegal Argument. Entity is not a work package!");
	// FacesContext.getCurrentInstance().addMessage("unique", msg);
	// e.printStackTrace();
	// }
	// editing = false;
	// return "";
	// }

	/**
	 * Gets all the projects and is annotated @Produces becuase it is being
	 * injected by a converter
	 */
	@Produces
	@AllProjects
	public List<Project> getAllProjects() {
		if (allProjects == null) {
			log.info("getting all Projects @ WorkPackageController.class");
			allProjects = projectAccess.getAllProjects();
		}
		return allProjects;
	}

	/**
	 * Indicates if the Project has been selected
	 * 
	 * @return true if not empty, false if empty
	 */
	public boolean isSelected() {
		return workPackage.getWpProject() != null;
	}

	/**
	 * Returns the workpackage being viewed or edited and is annotated @Produces
	 * so that it can be injected by the converter
	 * 
	 * @return Single workPackage
	 */
	@Produces
	@NewWorkPackage
	public WorkPackage getWorkPackage() {
		return workPackage;
	}

	/**
	 * sets current work package
	 * 
	 * @param workPackage
	 *            Pacakge to be selected
	 */
	public void setWorkPackage(WorkPackage workPackage) {
		this.workPackage = workPackage;
	}

	/**
	 * sets list of projects
	 * 
	 * @param allProjects
	 */
	public void setAllProjects(List<Project> allProjects) {
		this.allProjects = allProjects;
	}

	/**
	 * Ends the conversation if it has been started
	 */
	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			log.info("Conversation Ended @ WorkPackageControllerInterface.class");
		}
	}

	/**
	 * Starts the conversation if it hasn't been started
	 */
	public void startConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
			log.info("Conversation Started @ WorkPackageControllerInterface.class");
		}
	}

	/**
	 * Method that is called when an employee is dropped onto the work package
	 * which then adds them to workpackage
	 */
	public void onEmployeeDrop(DragDropEvent ddEvent) {
		Employee emp = ((Employee) ddEvent.getData());

		if (!workPackage.getWpEmployees().contains(emp)) {
			log.info("Adding employee to workPackage @ WorkPackageControllerInterface.class");
			workPackage.getWpEmployees().add(emp);
		}
		Collections.sort(workPackage.getWpEmployees(), new EmployeeComparer());
	}

	/**
	 * Called when an employee is dragged from the workpackage back to the
	 * project which results in the employee being removed from the workpackage
	 */
	public void onEmployeeDelete(DragDropEvent ddEvent) {
		Employee emp = ((Employee) ddEvent.getData());

		if (workPackage.getWpEmployees().contains(emp)) {
			log.info("Removing employee from workPackage @ WorkPackageControllerInterface.class");
			workPackage.getWpEmployees().remove(emp);
		}
		Collections.sort(workPackage.getWpEmployees(), new EmployeeComparer());
	}

	/**
	 * returns if the employee is editing a workpackage
	 *	@return true if editing otherwise false
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * Set the workpackage to editing
	 * @param editing True if editing otherwise false
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * returns the workpackage that is being viewed/edited
	 * @return viewed workPackage
	 */
	public WorkPackage getSelectedWorkPackage() {
		return selectedWorkPackage;
	}

	/**
	 * Set the workpackage to be viewed/edited
	 * @param selectedWorkPackage workPackage to be edited
	 */
	public void setSelectedWorkPackage(WorkPackage selectedWorkPackage) {
		startConversation();
		editing = false;
		log.info("Setting wp to be viewed/edited @ WorkPackageControllerInterface.class");
		this.workPackage = workPackageAccess
				.getWorkPackageByID(selectedWorkPackage);
	}
	
	@Override
	public Boolean getSubmitted()
	{
		return submitted;
	}
}
