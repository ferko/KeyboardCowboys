/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import org.primefaces.event.ToggleEvent;

import bcit.ca.infosys.KeyboardCowboys.model.Project;

/**
 * @author Andrej Koudlai
 * 
 */
public interface ProjectControllerInterface {

	/**
	 * Save the newly created project
	 * 
	 * @return Navigation Case
	 */
	public String saveProject();

	/**
	 * Updates the project that was being edited
	 * 
	 * @param proj
	 *            project to be updated
	 * @return Navigation case
	 */
	public String mergeProject();

	/**
	 * Check if the employee is editing a project
	 * 
	 * @return true if editing, otherwise false
	 */
	public boolean isEditing();

	/**
	 * Set the status of the project to editing or not editing
	 * 
	 * @param editing
	 *            true if editin, otherwise false
	 */
	public void setEditing(boolean editing);

	/**
	 * Gets all the projects for the user.
	 */
	public void displayProjects();

	/**
	 * Startes the conversation
	 */
	public void startConversation();

	/**
	 * Gets the project being selected
	 * 
	 * @return selected project
	 */
	public Project getProject();

	/**
	 * Gets all the projects
	 * 
	 * @return all projects
	 */
	public List<Project> getProjects();

	/**
	 * Set the project that is going to be viewed
	 * 
	 * @param project
	 *            project to be viewed
	 */
	public void setProject(Project project);

	/**
	 * Displays message when row expands (not in use)
	 * 
	 * @return
	 */
	public void onRowToggle(ToggleEvent event);

	List<Project> getFilteredProjects();

	void setFilteredProjects(List<Project> filteredProjects);

	Boolean getSubmitted();

}
