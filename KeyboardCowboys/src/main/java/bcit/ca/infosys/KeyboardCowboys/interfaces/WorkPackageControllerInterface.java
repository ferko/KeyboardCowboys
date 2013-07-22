/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import org.primefaces.event.DragDropEvent;

import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * Interface for Work Package controller
 * 
 * @author Andrej Koudlai
 * 
 */

public interface WorkPackageControllerInterface {

	/**
	 * Get the workPackage being viewed
	 * 
	 * @return selected workPackage
	 */
	public WorkPackage getSelectedWorkPackage();

	/**
	 * Sets the workPackage to be viewed/edited
	 * 
	 * @param wp
	 *            WorkPackage to be edited
	 */
	public void setSelectedWorkPackage(WorkPackage wp);

	/**
	 * Checks if a project has been selected
	 * 
	 * @return true if project selected
	 */
	public boolean isSelected();

	/**
	 * Persist work package to the database
	 * 
	 * @return Navigation Case
	 */
	public String saveWorkPackage();

	/**
	 * @author Andrej Used to update an existing work package contents into the
	 *         database
	 * @return Navigation Case
	 */
	public String mergeWorkPackage();

	/**
	 * gets the list of all projects
	 * 
	 * @return All projects
	 */
	public List<Project> getAllProjects();

	/**
	 * gets a current work package
	 * 
	 * @return single work Package
	 */
	public WorkPackage getWorkPackage();

	/**
	 * sets current work package
	 * 
	 * @param workPackage
	 *            selected workPackage
	 */
	public void setWorkPackage(WorkPackage workPackage);

	/**
	 * sets list of projects
	 * 
	 * @param allProjects
	 *            all the projects
	 */
	public void setAllProjects(List<Project> allProjects);

	/**
	 * ends the conversation
	 */
	public void endConversation();

	/**
	 * Starts the conversation
	 */
	public void startConversation();

	/**
	 * Adds and employee to the work package on drop
	 * 
	 * @param ddEvent
	 */
	public void onEmployeeDrop(DragDropEvent ddEvent);

	/**
	 * Removes a employee from the work package on drop
	 * 
	 * @param ddEvent
	 */
	public void onEmployeeDelete(DragDropEvent ddEvent);

	/**
	 * indicates if the user is editing the work Package
	 * 
	 * @return true if editing
	 */
	public boolean isEditing();

	/**
	 * Sets the workpackage to editing or not editing
	 * 
	 * @param editing
	 *            true if editing
	 */
	public void setEditing(boolean editing);

	Boolean getSubmitted();
}
