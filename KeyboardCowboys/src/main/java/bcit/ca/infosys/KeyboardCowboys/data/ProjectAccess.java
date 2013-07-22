package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Project;

/**
 * 
 * @author Jitin, Andrej
 *
 */
@Local(ProjectAccessInterface.class)
@Stateless
public class ProjectAccess implements ProjectAccessInterface{
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
	
	
	public List<Project> getAllProjects(){
		TypedQuery<Project> query = em.createQuery("Select DISTINCT p FROM Project p LEFT JOIN FETCH p.projAssistant LEFT JOIN FETCH p.projEmployees LEFT JOIN FETCH p.projWorkPackages LEFT JOIN FETCH p.projManager", Project.class);
		List<Project> projects = query.getResultList();
		return projects;
	}
	   /**
     * Returns a list of projects where a specific employee is a supervisor.
     * Takes an employee ID as a parameter and returns a list of projects.
     * @param empID
     * @return List<Project>
     */
    public List<Project> getSupervisorProjects(int empID){
        TypedQuery<Project> query = em.createQuery("Select p FROM Project p WHERE p.projManager = :empID", Project.class);
        List<Project> projects = query.setParameter("empID", empID).getResultList();
        return projects;
    }
    
    @Override
    public List<Project> getManagedProjects(Integer empID)
    {
    	TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p JOIN FETCH p.projManager WHERE p.projManager.empID = :empID", Project.class);
    	query.setParameter("empID", empID);
    	return query.getResultList();
    }
}
