package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;
import bcit.ca.infosys.KeyboardCowboys.util.EmployeeComparer;
/**
 * This class is responsible for retrieving employee from the Data Base
 * 
 * @author Jitin
 *
 */
@Local(EmployeeAccessInterface.class)
@Stateless
public class EmployeeAccess implements EmployeeAccessInterface{
	/**
	 * Entity Manager used to access the database
	 */
	@Inject
	private EntityManager em;
	/**
	 * Logger which is used to print events to the log
	 */
	@Inject
	private Logger log;
	
	@Override
	public boolean isApprover(Employee emp) 
	{
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.tsApprover = :emp", Employee.class).setParameter("emp", emp);
		return !query.getResultList().isEmpty();
	}
	
	/**
	 * Gets the Employee by user name along with the projects they are on
	 * @param userName of the employee
	 * @return employee from the db
	 */
	public Employee getEmployeeByUserName(String userName) {
		log.info("Getting " + userName + " from DB @ EmployeeAccess.class");
		TypedQuery<Employee> query = em.createQuery("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.tsApprover LEFT JOIN FETCH e.projects WHERE e.empUserName = :userName", Employee.class);
		Employee emp = query.setParameter("userName", userName).getResultList().get(0);
		for(Project p : emp.getManagedProjects())
				{
					Project proj = p;
				}
		for(WorkPackage wp : emp.getWpEngineered())
				{
					WorkPackage workP = wp;
				}
		return emp;
	}
	
	/**
	 * @author Andrej
	 * Retrieves a list of all employees from entity.
	 * @return List of all employees
	 */
    public List<Employee> getAllEmployees(){
		log.info("Getting all Employees from DB @ EmployeeAccess.class");
    	TypedQuery<Employee> query = em.createQuery("SELECT DISTINCT e FROM Employee e JOIN FETCH e.empPayInfo LEFT JOIN FETCH e.tsApprover LEFT JOIN FETCH e.empSuperVisor", Employee.class);
	    List<Employee> employees = query.getResultList();
	    return employees;
	}
    
    /**
     * Retrieves all the supervisors from the DB
     * @return supervisors from the DB
     */
    public List<Employee> getAllSuperVisors(){
		log.info("Getting supervisors from DB @ EmployeeAccess.class");
	    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.empRole = :super", Employee.class);
	    List<Employee> employees = query.setParameter("super", "SUPERVISOR").getResultList();
	    Collections.sort(employees, new EmployeeComparer());
	    return employees;
	}
    
	/**
	 * @author Jitin
	 * Gets all the employees that have not been assigned to a project
	 * @return returns a list of all unassigned employees to a project.
	 */
    @Override
	public List<Employee> getUnassignedEmployees(Employee currentEmployee){
		log.info("Getting unassigned employees from DB @ EmployeeAccess.class");
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.empSuperVisor = :super", Employee.class)
				.setParameter("super", currentEmployee);
		List<Employee> employees = query.getResultList();
		Collections.sort(employees, new EmployeeComparer());
		return employees;
	}
}
