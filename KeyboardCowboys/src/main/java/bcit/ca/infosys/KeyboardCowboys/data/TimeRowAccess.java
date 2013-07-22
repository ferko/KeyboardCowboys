package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeRowAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * Time Row access.
 * 
 * @author Andrej
 * 
 */

@Local(TimeRowAccessInterface.class)
@Stateless
public class TimeRowAccess implements TimeRowAccessInterface {

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
	public List<TimeRow> getTimeRowByDateEmployeeWorkPackage(Employee employee, Date date, WorkPackage workPackage) {
		TypedQuery<TimeRow> query = em
				.createQuery(
						"SELECT tr FROM TimeRow tr JOIN FETCH tr.trTimesheet ts WHERE tr.trWorkPackage = :wp AND ts.tsWeekEnding <= :date AND ts.tsEmp = :emp AND ts.status = 'Approved' ORDER BY ts.tsWeekEnding",
						TimeRow.class);
		query.setParameter("wp", workPackage);
		query.setParameter("date", date);
		query.setParameter("emp", employee);
		return query.getResultList();
	}
}
