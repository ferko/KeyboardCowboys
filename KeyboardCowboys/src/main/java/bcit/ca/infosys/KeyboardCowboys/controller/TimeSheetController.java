/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeSheetAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetApprovalRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;

/**
 * Controller responsible for activity related to time sheet.
 * 
 * @author Andrej, Jitin
 * 
 */
@Named("TimeSheetController")
@ConversationScoped
@Local(TimeSheetControllerInterface.class)
@Stateful
public class TimeSheetController implements Serializable,
        TimeSheetControllerInterface {
    /**
     * serializer
     */
    private static final long serialVersionUID = 1L;
    /**
     * Internally used logger
     */
    @Inject
    private Logger log;
    /**
     * Access interface to timesheet
     */
    @Inject
    private TimeSheetAccessInterface timeSheetAccess;
    /**
     * Access to registrar interface
     */
    @Inject
    private TimeSheetRegistrationInterface timeSheetRegistration;
    /**
     * Timesheet Approval Registrar interface
     */
    @Inject
    private TimeSheetApprovalRegistrationInterface timeSheetApprovalRegistration;
    /**
     * Employee injection
     */
    @Inject
    @CurrentEmployee
    private Employee employee;
    /**
     * Timsheet to enter into the database.
     */
    private TimeSheet timeSheet;
    private Boolean submitted = false;
    private TimeSheet rejectedTimeSheet;
    /**
     * List of all timesheets.
     */
    private Date tsDate;

    /**
     * Conversation injection
     */
    @Inject
    Conversation conversation;
    /**
     * Current employee injection
     */
    @Inject
    @CurrentEmployee
    private Employee currentEmp;

    /**
     * Persist timesheet to the database.
     * 
     * @return empty string
     */
    public String saveTimeSheet() {
        log.info("Saving Timesheet");
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Successfully created timeSheet.");
        try {
            timeSheet.setStatus("Saved");
            log.info(timeSheet.getTsTimeRows().get(0).getTrNotes());
            timeSheetRegistration.register(timeSheet);
            log.info("Timesheet saved");
        } catch (TimeSheetAlreadyExistsException e) {
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("TimeSheet already Exists!");
            FacesContext.getCurrentInstance().addMessage("formCreateTmSh", msg);
            e.printStackTrace();
            timeSheet.setStatus("Not Saved");
            log.info("Timesheet not saved");
            return "";
        }
        FacesContext.getCurrentInstance().addMessage("formCreateTmSh", msg);
        submitted = true;
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "";
    }

    /**
     * Saves changes to timesheet
     * 
     * @return empty string
     */
    public String mergeTimeSheet() {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Successfully updated timeSheet.");
        log.info(timeSheet.getTsTimeRows().get(0).getTrNotes());
        timeSheetRegistration.updateTimesheet(timeSheet);
        FacesContext.getCurrentInstance().addMessage("formCreateTmSh", msg);
        submitted = true;
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "";
    }

    /**
     * Submits timesheet
     * 
     * @return empty string
     */
    public String submitTimeSheet() {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Successfully updated timeSheet.");
        TimeSheetApproval tsa = new TimeSheetApproval();
        tsa.setTimeSheet(timeSheet);
        tsa.setTsaApprover(currentEmp.getTsApprover());
        String stat = timeSheet.getStatus();
        timeSheet.setStatus("Submitted");
        timeSheetRegistration.updateTimesheet(timeSheet);
        if(!stat.equals("Rejected"))
        {
        	timeSheetApprovalRegistration.registerTimeSheetApproval(tsa);
        }
        FacesContext.getCurrentInstance().addMessage("formCreateTmSh", msg);

        submitted = true;
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "";
    }

    /**
     * time sheet approval
     */
    public void approveTimeSheet() {

    }

    /**
     * Setter for timesheet.
     * 
     * @param timeSheet
     */
    public void setTimeSheet(TimeSheet timeSheet) {
        System.out.println("### SETTING TIMESHEET ###");
        this.timeSheet = timeSheet;
    }

    /**
     * Getter for timeSheet.
     * 
     * @return
     */
    public TimeSheet getTimeSheet() {
        return this.timeSheet;
    }

    /**
     * Conversation starter
     */
    public void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            log.info("Conversation Started");
        }
    }

    /**
     * Getter for currently logged in employee
     * 
     * @return current employee
     */
    public Employee getCurrentEmp() {
        return currentEmp;
    }

    /**
     * Need to implement to get the week of year based on end of week attribute
     * in given timesheet.
     */
    public void getWeekNumber(TimeSheet t) {

    }

    // code below moved to model.
    // /**
    // * Returns grand total of hours worked. Adds totals of all rows.
    // *
    // * @author Andrej K
    // * @return result that is the grand total of all rows totals.
    // */
    // public double getTotal() {
    // double result = 0;
    // for (TimeRow tr : timeSheet.getTsTimeRows()) {
    // result += tr.getTotal();
    // }
    // return result;
    // }
    //
    // /**
    // * Returns totals for each day of the row.
    // *
    // * @author Andrej K
    // * @return result that is the total of each day of the week.
    // * @param day is the day of the week passed in from view.
    // */
    // public double getTotalForDay(int day) {
    // double result = 0;
    //
    // for (TimeRow row : timeSheet.getTsTimeRows()) {
    // switch (day) {
    // case 0:
    // result += row.getTrSat();
    // break;
    // case 1:
    // result += row.getTrSun();
    // break;
    // case 2:
    // result += row.getTrMon();
    // break;
    // case 3:
    // result += row.getTrTue();
    // break;
    // case 4:
    // result += row.getTrWed();
    // break;
    // case 5:
    // result += row.getTrThu();
    // break;
    // case 6:
    // result += row.getTrFri();
    // }
    // }
    // return result;
    // }
    /**
     * TS Date Getter
     * 
     * @return tsDate
     */
    public Date getTsDate() {
        return tsDate;
    }

    /**
     * Setter for timesheet date
     * @param Timesheet Date
     */
    public void setTsDate(Date tsDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tsDate);
        calendar.set(Calendar.DAY_OF_WEEK, 6);
        this.tsDate = calendar.getTime();
        this.timeSheet = timeSheetAccess.getTimeSheetByDate(this.tsDate,
                employee.getEmpID());
    }

    /**
     * If timesheet was submitted
     * @return true if submitted, false if not.
     */
    @Override
    public Boolean getSubmitted() {
        return submitted;
    }

    @Override
	public TimeSheet getRejectedTimeSheet() {
		return rejectedTimeSheet;
	}

    @Override
	public void setRejectedTimeSheet(TimeSheet rejectedTimeSheet) {
    	startConversation();
		timeSheet = timeSheetAccess.getTimeSheetByID(rejectedTimeSheet.getTsID());
	}
}
