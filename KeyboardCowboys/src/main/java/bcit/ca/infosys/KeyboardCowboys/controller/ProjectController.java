/**
 * 
 */
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

import org.primefaces.event.ToggleEvent;

import bcit.ca.infosys.KeyboardCowboys.exceptions.ProjectAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetProjectEmployees;

/**
 * 
 * This class is used by multiple views to display information about projects,
 * view a list of projects and also to create projects
 * 
 * @author Andrej Koudlai, Jitin D
 * 
 */
@Named("ProjectController")
@ConversationScoped
@Local(ProjectControllerInterface.class)
@Stateful
public class ProjectController implements Serializable,
		ProjectControllerInterface {

	/**
	 * Logger which is used to log method calls
	 */
	@Inject
	private Logger log;

	/**
	 * Injected stateless session bean which allows reading of projects from the
	 * database
	 */
	@Inject
	private ProjectAccessInterface projectAccess;

	/**
	 * Default serializeable id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Inject conversation which is used to begin and end conversations
	 */
	@Inject
	Conversation conversation;

	/**
	 * Injected statless session bean which is used to write projects to the
	 * database
	 */
	@Inject
	private ProjectRegistrationInterface projectRegistration;

	/**
	 * List of all the according projects.
	 */
	private List<Project> projects;
	private List<Project> filteredProjects;

	/**
	 * Used to indicate weather the user is viewing or editing a project
	 */
	private boolean editing;
	/**
	 * Project that is being input by the form UI.
	 */
	private Project project;
	private Boolean submitted = false;

	/**
	 * Constructor for class, creates new Project.
	 */
	public ProjectController() {
		project = new Project();
	}

	/**
	 * 
	 * Saves the new project to the database using the projectRegistration.class
	 * so that it can be stored.
	 * 
	 * @return Navigation Case
	 */
	public String saveProject() {
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Successfully created project.");
		try {
			log.info("Registering Project @ ProjectController.class");
			projectRegistration.register(project);
		} catch (ProjectAlreadyExistsException e) {
			log.info("Project already exists @ ProjectController.class");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setSummary("Project already Exists!");
			FacesContext.getCurrentInstance().addMessage("unique", msg);
			e.printStackTrace();
			return "";
		}
		if (!conversation.isTransient()) {
			conversation.end();
			log.info("Conversation Ended @ ProjectController.class");
		}
		submitted = true;
		FacesContext.getCurrentInstance().addMessage("unique", msg);
		if (!conversation.isTransient()) {
			conversation.end();
			log.info("Converation Started @ ProjectController.class");
		}
		return "";
	}

	/**
	 * Starts a coversation and then goes to the database to get a list of
	 * projects
	 */
	public void displayProjects() {
		startConversation();
		if (projects == null) {
			projects = projectAccess.getAllProjects();
			log.info("Getting all the projects @ ProjectController.class");
		}
	}

	/**
	 * Starts the conversation for the bean
	 */
	public void startConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
			log.info("Converation Started @ ProjectController.class");
		}
	}

	/**
	 * Returns the project being edited and it is a producer method so that the
	 * faces converter can use it
	 * 
	 * @return project being edited
	 */
	@Produces
	@GetProjectEmployees
	public Project getProject() {
		return project;
	}

	/**
	 * Returns all the projects
	 * 
	 * @return all projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * Used to set the project so that it can be edited
	 * 
	 * @param project
	 *            project to be edited
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Displays message when row expands (not in use)
	 * 
	 * @param event
	 */
	public void onRowToggle(ToggleEvent event) {
		/*
		 * FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
		 * "Title message", "Content message");
		 * 
		 * FacesContext.getCurrentInstance().addMessage(null, msg);
		 */
	}

	/**
	 * returns whether the user is editing a project or viewing on
	 * 
	 * @return true if editing, otherwise false
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * used to indicate that the user is wanted to edit the project
	 * 
	 * @param editing
	 *            true for editng, otherwise false
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * Used to update a project after it has been edited
	 * 
	 * @return Navigation Case
	 */
	public String mergeProject() {
		FacesMessage msg = new FacesMessage();
		try {
			log.info("Merging Project @ ProjectController.class");
			projectRegistration.update(project);
		} catch (IllegalArgumentException e) {
			log.info("Failed merging project @ ProjectController.class");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setSummary("Illegal Argument. Entity is not a work package!");
			FacesContext.getCurrentInstance().addMessage("unique", msg);
			e.printStackTrace();
		}
		editing = false;
		return "";
	}

	/**
	 * Getter for filtered projects
	 * @return list of filtered projects
	 */
	public List<Project> getFilteredProjects() {
		return filteredProjects;
	}


	/**
	 * Setter for filtered projects
	 */
	public void setFilteredProjects(List<Project> filteredProjects) {
		this.filteredProjects = filteredProjects;
	}


	/**
	 * Checks if submission was successful
	 * @return true if submission successful.
	 */
	public Boolean getSubmitted() {
		return submitted;
	}
}
