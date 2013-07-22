package bcit.ca.infosys.KeyboardCowboys.service;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeRowAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeRowRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;

/**
 * Registers and persists Time Row to the database
 * 
 * @author Andrej K
 * 
 */
@Local(TimeRowRegistrationInterface.class)
@Stateless
public class TimeRowRegistration implements TimeRowRegistrationInterface{

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
	
    public void register(TimeRow timeRow) throws TimeRowAlreadyExistsException {
        if (em.find(TimeRow.class, timeRow.getTrID()) != null) {
            throw new TimeRowAlreadyExistsException(
                    "TimeRow already exists in database");
        }
        em.persist(timeRow);
    }
}
