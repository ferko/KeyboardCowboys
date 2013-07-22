/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;

/**
 * Interface for TimeSheet controller
 * 
 * @author Andrej Koudlai
 * 
 */
public interface TimeSheetControllerInterface {

    /**
     * Persist timesheet to the database.
     */
    public String saveTimeSheet();

    /**
     * @return 
	 * 
	 */
    public String mergeTimeSheet();

    /**
	 * 
	 */
    public void approveTimeSheet();

    /**
     * Setter for timesheet.
     * 
     * @param timeSheet
     */
    public void setTimeSheet(TimeSheet timeSheet);

    /**
     * Getter for timeSheet.
     * 
     * @return
     */
    public TimeSheet getTimeSheet();


    public void startConversation();

    public Employee getCurrentEmp();

    public void getWeekNumber(TimeSheet t);

    
	public Date getTsDate();
	public void setTsDate(Date tsDate);
    public String submitTimeSheet();

	Boolean getSubmitted();

	void setRejectedTimeSheet(TimeSheet rejectedTimeSheet);

	TimeSheet getRejectedTimeSheet();
}
