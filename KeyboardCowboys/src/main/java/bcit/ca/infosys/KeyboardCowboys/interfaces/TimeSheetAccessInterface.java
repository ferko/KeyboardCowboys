package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;
import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;

/**
 * Interface for timesheet
 * 
 * @author Andrej Koudlai
 *
 */
public interface TimeSheetAccessInterface {

    public List<TimeSheet> getAllTimeSheetsByEmp(Employee currentEmp);
	public List<TimeSheet> getAllTimeSheets();
	public TimeSheet getTimeSheetByID(int tsID);
	public TimeSheet getTimeSheetByDate(Date tsDate, Integer eID);
	List<TimeSheet> getRejectedTimeSheets(Employee currentEmployee);
}
