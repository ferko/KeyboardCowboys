package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;



import bcit.ca.infosys.KeyboardCowboys.model.Project;

/**
 * 
 * @author Andrej Koudlai
 *
 */

public interface ProjectAccessInterface {

	
	public List<Project> getAllProjects();
	   /**
     * Returns a list of projects where a specific employee is a supervisor.
     * Takes an employee ID as a parameter and returns a list of projects.
     * @param empID
     * @return List<Project>
     */
    public List<Project> getSupervisorProjects(int empID);
	List<Project> getManagedProjects(Integer empID);
}
