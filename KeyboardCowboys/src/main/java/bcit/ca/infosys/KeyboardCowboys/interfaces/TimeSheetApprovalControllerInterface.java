package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
/**
 * 
 * @author Andrej K
 *
 *
 *Allowed statuses APPROVED, REJECTED, SUBMITTED, SAVED
 */
public interface TimeSheetApprovalControllerInterface {
    /**
     * Starting conversation
     */
    public void startConversation();
    /**
     * Ends conversation
     */
    public void endConversation();
    /**
     * Approve the current timesheet by changing status to APPROVED all caps
     * Use Timesheet registration interface (class) to merge time sheet.
     * @return 
     */
    public String ApproveTimeSheet();
    /**
     * same as approve but different msg
     * @return
     */
    public String RejectTimeSheet();
    /**
     * get all timesheets pending approval from TimeSheetApproval entity stored in the controller
     * @return
     */
    public List<TimeSheetApproval> getTimeSheetApprovals();
    /**
     * Gets a timesheet from the timesheet object
     * @return
     */
    public TimeSheetApproval getTimeSheetApproval();
    /**
     * Set timesheet object 
     * Sets time sheet 
     */
    public void setTimeSheetApproval(TimeSheetApproval t);
    
    /**
     * Gets list of TimeSheetApproval and populates it to the controller list.
     * Annotate with @postconstruct
     */
    public void initTimeSheetApprovals();
    
    public void setTimeSheetApprovalStatus(String status);
	String getTimeSheetApprovalNotes();
	void setTimeSheetApprovalNotes(String timeSheetApprovalNotes);
    
        
}
