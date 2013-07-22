package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;
/**
 * Interface for SupervisorController methods.
 * 
 * @author Andrej Koudlai
 */
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;

/**
 * Interface for Supervisor Controller
 * 
 * @author Andrej K
 *
 */

public interface SupervisorControllerInterface {

    public void getUnassignedEmployees();

    public List<Project> getAllProjects();

    public void startConversation();

    public void endConversation();

    public void saveEmployee();

    public List<Employee> getEmployees();

    public List<Project> getProjects();

    public Employee getEmployee();

    public void setEmployee(Employee employee);

    public Project getProject();

    public void setProject(Project project);

	Boolean getSubmitted();
}
