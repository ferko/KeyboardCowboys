package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WeeklyReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WeeklyReportControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WeeklyReportRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReport;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReportRow;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.WRProjects;

/**
 * Controller for the Weekly Report
 * 
 * @author Frank Berenyi
 *
 */
@ConversationScoped
@Named("WeeklyReportController")
@Local(WeeklyReportControllerInterface.class)
@Stateful
public class WeeklyReportController implements Serializable,
		WeeklyReportControllerInterface {

	/**
	 * Serializer
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	/**
	 * Internally used logger
	 */
	private Logger log;
	/**
	 * Interface to Access weekly report
	 */
	@Inject
	private WeeklyReportAccessInterface weeklyReportAccess;
	/**
	 * Conversation injector
	 */
	@Inject
	private Conversation conversation;
	/**
	 * Interface registrar for weekly report
	 */
	@Inject
	private WeeklyReportRegistrationInterface weeklyReportRegistration;
	/**
	 * Date
	 */
	private Date date;
	/**
	 * Project
	 */
	private Project project;
	/**
	 * Weekly report
	 */
	private WeeklyReport weeklyReport;
	/**
	 * List of projects
	 */
	private List<Project> projects;
	/**
	 * Interface to access projects
	 */
	@Inject
	private ProjectAccessInterface projectAccess;
	/**
	 * Injects current employee
	 */
	@Inject
	@CurrentEmployee
	private Employee currentEmployee;
	/**
	 * List of employees
	 */
	private List<Employee> emps;
	/**
	 * List of workpackages
	 */
	private List<WorkPackage> wps;
	
	/**
	 * Conversation starter
	 */
	public void startConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
			log.info("Conversation started @ WeeklyReportController.class");
			getProjects();
		}
	}
	/**
	 * Conversation ender
	 */
	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			log.info("Conversation ended @ WeeklyReportController.class");
		}
	}

	/**
	 * Weekly report getter
	 * @return weekly report
	 */
	// private Map twrEmployee;
	// private Map twrWorkPackage;
	@Override
	public WeeklyReport getWeeklyReport() {

		if(weeklyReport == null)
		{
		weeklyReport = weeklyReportAccess.getWeeklyReportByProject(project,
				date);
		}
		return weeklyReport;
	}

	/**
	 * Getter for the report date
	 * @return date
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * Report date setter
	 * @param date
	 */
	@Override
	public void setDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 6);
		this.date = calendar.getTime();
		getWeeklyReport();
	}

	/**
	 * Project getter
	 * @return project
	 */
	@Override
	public Project getProject() {
		return project;
	}

	/**
	 * Project setter
	 * @param Project
	 */
	@Override
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Uses the weeklyReportRegistration bean to persist the weekly report into
	 * the database
	 * 
	 * @return
	 */
	public String saveWeeklyReport() {
		return "";
	}

	/**
	 * Uses the weeklyReportRegistration bean to merge the weekly report into
	 * the database
	 * 
	 * @return
	 */
	public String updateWeeklyReport() {
		return "";
	}

	/**
	 * Changes the statues to Submitted and then uses the
	 * weeklyReportRegistration bean to merge the weekly report into the
	 * database
	 * 
	 * @return
	 */
	public String submitWeeklyReport() {
		return "";
	}

	/**
	 * Producer. Sets initial projects from database
	 * @return projects
	 */
	@Produces
	@WRProjects
	@Override
	public List<Project> getProjects() {
		if (projects == null) {
			projects = projectAccess.getManagedProjects(currentEmployee
					.getEmpID());
		}
		return projects;
	}

	/**
	 * Getter for employees
	 * @return employees
	 */
	@Override
	public List<Employee> getEmps() {
		emps = new ArrayList<Employee>();
		if (weeklyReport != null) {
			for (Employee emp : weeklyReport.getEmpMap().keySet()) {
				emps.add(emp);
			}
		}
		return emps;
	}

	/**
	 * Get list of workpackages
	 * @return workpackages list
	 */
	@Override
	public List<WorkPackage> getWps() {
		wps = new ArrayList<WorkPackage>();
		if (weeklyReport != null) {
			for (WorkPackage wp : weeklyReport.getWpMap().keySet()) {
				wps.add(wp);
			}
		}
		return wps;
	}
	/**
	 * Calculator for totals of the current week
	 * @param list (row of weekly report)
	 * @return result - double
	 */
	@Override
	public Double getTotalCurrentWeek(List<WeeklyReportRow> list)
	{
		Double result = 0.0;
		for(WeeklyReportRow wrr : list)
		{
			result += wrr.getCurrentWeek();
		}
		return result;
	}
	
	/**
	 * Calculator for current month totals
	 * @param list (row of weekly report)
     * @return result - double
	 */
	@Override
	public Double getTotalCurrentMonth(List<WeeklyReportRow> list)
	{
		Double result = 0.0;
		for(WeeklyReportRow wrr : list)
		{
			result += wrr.getCurrentMonth();
		}
		return result;
	}
	/**
	 * Grand total calculator
	 * @param Weekly Report Rows
	 * @return result
	 */
	@Override
	public Double getTotal(List<WeeklyReportRow> list)
	{
		Double result = 0.0;
		for(WeeklyReportRow wrr : list)
		{
			result += wrr.getWrrTotal();
		}
		return result;
	}
}
