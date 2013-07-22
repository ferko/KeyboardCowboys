package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.interfaces.MonthlyReportControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.MonthyReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetMRProjects;
/**
 * Montly Report Controller is responsible for retrieving 
 * 
 * 
 * @author Andrej Koudlai
 *
 */
@Named("MonthlyReportController")
@Stateful
@ConversationScoped
@Local(MonthlyReportControllerInterface.class)
public class MonthlyReportController implements Serializable, MonthlyReportControllerInterface{

	/**
	 * Serializer
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Interface to access report
	 */
	@Inject
	private MonthyReportAccessInterface monthlyReportAccess;
	/**
	 * Conversation
	 */
	@Inject
	private Conversation conversation;
	/**
	 * Interface to access project
	 */
	@Inject
	private ProjectAccessInterface projectAccess;
	/**
	 * Currently logged in employee injection
	 */
	@Inject
	@CurrentEmployee
	private Employee currentEmp;
	/**
	 * Date variable
	 */
	private Date month;
	/**
	 * Project holder
	 */
	private Project project;
	/**
	 * List of projects
	 */
	private List<Project> projects;
	/**
	 * Map with string as a key and status report as a value
	 */
	private Map<String, StatusReport> monthlyReports;
	
	/**
	 * getter for month
	 * 
	 * @return month
	 */
	public Date getMonth() {
		return month;
	}

	/**
	 * Month setter
	 */
	public void setMonth(Date month) {
		this.month = month;
		monthlyReports = monthlyReportAccess.getMonthyReport(project, month);
	}

	/**
	 * Project getter
	 * 
	 * @return project of the report
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Project setter
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	/**
	 * Producer method that retrieves a list of managed projects using project access interface.
	 * @return list of managed projects
	 */
	@Produces
	@GetMRProjects
	public List<Project> getProjects() {
		if(projects == null)
		{
			projects = projectAccess.getManagedProjects(currentEmp.getEmpID());
		}
		return projects;
	}
	

	/**
	 * Conversation starter
	 */
	public void startConversation()
	{
		if(conversation.isTransient())
		{
			conversation.begin();
		}
	}
	

	/**
	 * Conversation ender
	 */
	public void endConversation()
	{
		if(!conversation.isTransient())
		{
			conversation.begin();
		}
	}


	/**
	 * Getter for monthly reports
	 * @return map of reports
	 */
	public Map<String, StatusReport> getMonthlyReports() {
		return monthlyReports;
	}

	/**
	 * Setter for monthly reports that takes a map of reports as parameters
	 * @param monthlyReports
	 */
	public void setMonthlyReports(Map<String, StatusReport> monthlyReports) {
		this.monthlyReports = monthlyReports;
	}
	

	/**
	 * Getter for working set of every key/value in the map
	 * @return key/value set list of the report
	 */
	public List<String> getWps() {
		List<String> wps = new ArrayList<String>();
			for (String wp : monthlyReports.keySet()) {
				wps.add(wp);
			}
				return wps;
	}
}
