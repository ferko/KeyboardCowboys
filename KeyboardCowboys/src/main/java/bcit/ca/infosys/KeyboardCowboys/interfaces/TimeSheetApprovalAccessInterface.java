package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;

/**
 * 
 * 
 * @author Andrej K
 *
 */


public interface TimeSheetApprovalAccessInterface {


    public List<TimeSheetApproval> getTimeSheetApprovalsByEmployee(Employee employee);
}
