package bcit.ca.infosys.KeyboardCowboys.service;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeSheetAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;

/**
 * Registers and persists timesheets to the database
 * 
 * @author Andrej K
 *
 */

@Local(TimeSheetRegistrationInterface.class)
@Stateless
public class TimeSheetRegistration implements TimeSheetRegistrationInterface{
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
	

    public void register(TimeSheet timeSheet)
            throws TimeSheetAlreadyExistsException {
        /*if (em.find(TimeSheet.class, timeSheet.getTsID()) != null) {
            throw new TimeSheetAlreadyExistsException(
                    "TimeSheet already exists in database");
        }*/
        em.persist(timeSheet);
    }
    
    public void updateTimesheet(TimeSheet ts){
        em.merge(ts);
    }
}
