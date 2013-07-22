package bcit.ca.infosys.KeyboardCowboys.service;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import bcit.ca.infosys.KeyboardCowboys.exceptions.ProjectAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Project;

/**
 * Registers and updates Project 
 * 
 * @author Andrej K
 *
 */

@Local(ProjectRegistrationInterface.class)
@Stateless
public class ProjectRegistration implements ProjectRegistrationInterface{

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
	

    public void register(Project proj) throws ProjectAlreadyExistsException {
        if (em.find(Project.class, proj.getProjID()) != null) {
            throw new ProjectAlreadyExistsException(
                    "Project already exists in database");
        }
        em.persist(proj);
    }
    
    public void update(Project proj) {
        em.merge(proj);
    }
}
