package bcit.ca.infosys.KeyboardCowboys.service;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import bcit.ca.infosys.KeyboardCowboys.exceptions.PrincipleAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PrincipleRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

/**
 * Registers Principle.
 * 
 * @author Andrej K
 * 
 */

@Local(PrincipleRegistrationInterface.class)
@Stateless
public class PrincipleRegistration implements PrincipleRegistrationInterface {
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

	public void register(Principle principle)
			throws PrincipleAlreadyExistsException {
		if (em.find(Principle.class, principle.getEmp().getEmpID()) != null) {
			log.info("Principle with name:\'"
					+ principle.getEmp().getEmpUserName()
					+ "\' already exists @ PrincipleRegistration.class");
			throw new PrincipleAlreadyExistsException(
					"Principle already exists in database");
		}
		principle.setEmpPassword(SHAHash.hash(principle.getEmpPassword()));
		em.persist(principle);
		log.info("Principle with name:\'" + principle.getEmp().getEmpUserName()
				+ "\' registered @ PrincipleRegistration.class");
	}

	@Override
	public void merge(Principle principle) {
		if (em.createQuery("SELECT p FROM Principle p WHERE p.emp = :emp AND p.empPassword = :pass", Principle.class).setParameter("emp", principle.getEmp())
				.setParameter("pass", principle.getEmpPassword()).getResultList().isEmpty()) {
			principle.setEmpPassword(SHAHash.hash(principle.getEmpPassword()));
			em.merge(principle);
		}
	}
}
