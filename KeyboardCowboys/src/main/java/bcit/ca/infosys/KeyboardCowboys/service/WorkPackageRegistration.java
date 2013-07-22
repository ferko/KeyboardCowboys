package bcit.ca.infosys.KeyboardCowboys.service;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import bcit.ca.infosys.KeyboardCowboys.exceptions.WorkPackageAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * Registers a new work package and merger.
 * 
 * @author Andrej K
 * 
 */

@Local(WorkPackageRegistrationInterface.class)
@Stateless
public class WorkPackageRegistration implements
		WorkPackageRegistrationInterface {
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

	public void register(WorkPackage wPackage)
			throws WorkPackageAlreadyExistsException {
		if (!em.createQuery(
				"SELECT w FROM WorkPackage w WHERE w.wpProject = :proj AND w.wpID = :id",
				WorkPackage.class)
				.setParameter("proj", wPackage.getWpProject())
				.setParameter("id", wPackage.getWpID()).getResultList().isEmpty())
				{
			throw new WorkPackageAlreadyExistsException(
					"WorkPackage already exists in database");
		}
		em.persist(wPackage);
	}

	public void mergeWorkPackage(WorkPackage workPackage) {
		em.merge(workPackage);
	}
}
