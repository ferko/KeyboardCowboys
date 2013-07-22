package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.interfaces.LoginControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.StatusReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.StatusReportControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.StatusReportRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetSRWorkPackages;

/**
 * Controller that allows ineraction with status report
 * 
 * @author Frank Berenyi
 */
@ConversationScoped
@Named("StatusReportController")
@Local(StatusReportControllerInterface.class)
@Stateful
public class StatusReportController implements Serializable,
        StatusReportControllerInterface {

    /**
	 * Serializer
	 */
    private static final long serialVersionUID = 1L;
    /**
	 * Logger for internal purposes 
	 */
    @Inject
    private Logger log;
    /**
	 * Interface to access Status Reports
	 */
    @Inject
    private StatusReportAccessInterface statusReportAccess;
    /**
	 * Conversation injection
	 */
    @Inject
    private Conversation conversation;
    /**
	 * Interface to register a new status report
	 */
    @Inject
    private StatusReportRegistrationInterface statusReportRegistration;
    /**
	 * Interface to access work package
	 */
    @Inject
    private WorkPackageAccessInterface workPackageAccess;
    /**
	 * Responsible for login authentication
	 */
    @Inject
    private LoginControllerInterface loginController;
    /**
	 * Status report object
	 */
    private StatusReport statusReport;
    /**
	 * List of associated workpackages
	 */
    private List<WorkPackage> workPackages;
    /**
	 * Report Date
	 */
    private Date reportDate;
    /**
	 * Work package
	 */
    private WorkPackage workPackage;

    /**
	 * Starts conversation
	 */
    public void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            log.info("Conversation started");
        }
    }

    /**
	 * ends conversation
	 */
    public void endConversation() {
        // TODO Auto-generated method stub

    }

    /**
	 * gets status report
	 * @return status report
	 */
    @Override
    public StatusReport getStatusReport() {
        return statusReport;
    }

    /**
	 * sets status report
	 */
    @Override
    public String saveStatusReport() {
        FacesMessage msg = new FacesMessage();
        statusReport.setStatus("SAVED");
        statusReportRegistration.saveStatusReport(statusReport);
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Status report saved successfully");
        FacesContext.getCurrentInstance().addMessage("formViewStatRpt", msg);
        return null;
    }

    /**
     * Submits status report and sets its status to submitted
     */
    @Override
    public String submitStatusReport() {
        FacesMessage msg = new FacesMessage();
        statusReport.setStatus("SUBMITTED");
        statusReportRegistration.mergeStatusReport(statusReport);
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Status report submitted successfully");
        FacesContext.getCurrentInstance().addMessage("formViewStatRpt", msg);
        return null;
    }

    /**
     * Status report updater.
     */
    @Override
    public String updateStatusReport() {
        FacesMessage msg = new FacesMessage();
        statusReportRegistration.mergeStatusReport(statusReport);
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Status report updated successfully");
        FacesContext.getCurrentInstance().addMessage("formViewStatRpt", msg);
        return null;
    }

    /**
     * initialize status report by work package and report date.
     * Logger keeps track.
     */
    public void initStatusReport() {
        statusReport = statusReportAccess.getStatusReportByWP(workPackage,
                reportDate);
        log.info("" + statusReport.getWorkPackage().getWpName());
    }

    /**
     * Producer that returns list of all workpackages in the database
     * @return workpackages in db
     */
    @Produces
    @GetSRWorkPackages
    public List<WorkPackage> getWorkPackages() {
        if (workPackages == null) {
            workPackages = workPackageAccess
                    .getWorkPackageByResEngineer(loginController
                            .getCurrentEmployee());
        }
        return workPackages;
    }

    /**
     * Returns status report date
     * @return report date
     */
    public Date getReportDate() {
        return reportDate;
    }

    /**
     * Setter for the report date
     */
    public void setReportDate(Date reportDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(reportDate);
        calendar.set(Calendar.DAY_OF_WEEK, 6);
        this.reportDate = calendar.getTime();
        initStatusReport();
    }

    /**
     * Getter for workpackage
     * @return workpackage
     */
    public WorkPackage getWorkPackage() {
        return workPackage;
    }

    /**
     * Setter for workpackage
     */
    public void setWorkPackage(WorkPackage workPackage) {
        log.info("WorkPackageset:" + workPackage.getWpID());
        this.workPackage = workPackage;
    }

}
