package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetApprovalAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetApprovalControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;

/**
 * Controller that allows to approve time sheets.
 * 
 * @author Andrej Koudlai
 * 
 */
@Named("TimeSheetApprovalController")
@ConversationScoped
@Local(TimeSheetApprovalControllerInterface.class)
@Stateful
public class TimeSheetApprovalController implements Serializable,
        TimeSheetApprovalControllerInterface {

    private static final long serialVersionUID = 1L;

    /**
     * Injection of the conversation for the conversation scoped bean.
     */
    @Inject
    private Conversation conversation;

    @Inject
    @CurrentEmployee
    private Employee currentEmployee;
    /**
     * Injected log used to log that status of the bean.
     */
    @Inject
    private Logger log;

    /**
     * Time Sheet Access Interface injection
     */
    @Inject
    private TimeSheetAccessInterface timeSheetAccess;

    /**
     * TimeSheetApproval Access Interface injection
     */
    @Inject
    private TimeSheetApprovalAccessInterface TimeSheetApprovalAccess;

    /**
     * Injection of time sheet registrar
     */
    @Inject
    private TimeSheetRegistrationInterface timeSheetRegistration;

    /**
     * Timesheet Approval 
     */
    private TimeSheetApproval timeSheetApproval;

    /**
     * Multiple timesheet approvals
     */
    private List<TimeSheetApproval> timeSheetApprovals;
    /**
     * Notes of those that are approving timesheets
     */
    private String timeSheetApprovalNotes;

    /**
     * Conversation starter
     */
    public void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            log.info("Conversation started @ ApproveTimeSheetController.class");
        }
    }

    /**
     * Conversation ender
     */
    public void endConversation() {
        conversation.end();
    }

    /**
     * Approves timesheet and returns null
     * @return null
     */
    public String ApproveTimeSheet() {
        timeSheetApproval.getTimeSheet().setStatus("Approved");
    	timeSheetApproval.getTimeSheet().setApprovalNotes(timeSheetApprovalNotes);
        timeSheetRegistration.updateTimesheet(timeSheetApproval.getTimeSheet());
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Timesheet approved");
		FacesContext.getCurrentInstance().addMessage("timesheetTable", msg);
		timeSheetApprovals.remove(timeSheetApproval);
        return null;
    }

    /**
     * Rejects timesheet. Returns null.
     * @return null
     */
    public String RejectTimeSheet() {
    	timeSheetApproval.getTimeSheet().setStatus("Rejected");
    	timeSheetApproval.getTimeSheet().setApprovalNotes(timeSheetApprovalNotes);
        timeSheetRegistration.updateTimesheet(timeSheetApproval.getTimeSheet());
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Timesheet rejected");
		FacesContext.getCurrentInstance().addMessage("timesheetTable", msg);
		timeSheetApprovals.remove(timeSheetApproval);
		return null;
    }
    /**
     * Status of the approval setter
     *
     */
    public void setTimeSheetApprovalStatus(String status){
    	timeSheetApproval.getTimeSheet().setStatus(status);
        timeSheetRegistration.updateTimesheet(timeSheetApproval.getTimeSheet());
    }

    /**
     * Getter for time sheet approvals
     * @return time sheet approvals
     */
    public List<TimeSheetApproval> getTimeSheetApprovals() {
        return this.timeSheetApprovals;
    }

    /**
     * get timesheet approval
     * @return time sheet approval
     */
    public TimeSheetApproval getTimeSheetApproval() {
        return timeSheetApproval;
    }

    /**
     * setter for time sheet approval.
     * @param timeSheet approval
     */
    public void setTimeSheetApproval(TimeSheetApproval timeSheetApproval) {
        this.timeSheetApproval = timeSheetApproval;
    }

    /**
     * Post constructor that initialises time sheet approvals by current employee
     */
    @PostConstruct
    public void initTimeSheetApprovals() {
        startConversation();
        this.timeSheetApprovals = TimeSheetApprovalAccess
                .getTimeSheetApprovalsByEmployee(currentEmployee);
    }

    /**
     * Getter for timesheet approval notes
     * @return notes
     */
    @Override
	public String getTimeSheetApprovalNotes() {
		return timeSheetApprovalNotes;
	}
    
    /**
     * Setter for notes
     * @param String notes
     */
	@Override
	public void setTimeSheetApprovalNotes(String timeSheetApprovalNotes) {
		this.timeSheetApprovalNotes = timeSheetApprovalNotes;
	}

}
