package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;
import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * Interface for Time Row
 * 
 * @author Andrej
 *
 */
public interface TimeRowAccessInterface {
	List<TimeRow> getTimeRowByDateEmployeeWorkPackage(Employee employee, Date date,
			WorkPackage workPackage);
}
