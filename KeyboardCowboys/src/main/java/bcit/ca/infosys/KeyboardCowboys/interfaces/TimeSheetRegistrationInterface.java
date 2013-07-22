package bcit.ca.infosys.KeyboardCowboys.interfaces;


import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeSheetAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;

/**
 * Interface for TimeSheet registration
 * 
 * @author Andrej K
 *
 */
public interface TimeSheetRegistrationInterface {

    public void register(TimeSheet timeSheet) throws TimeSheetAlreadyExistsException;
    public void updateTimesheet(TimeSheet ts);
}
