package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;

import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetMRProjects;

public interface MonthlyReportControllerInterface {

	@Produces
	@GetMRProjects
	public List<Project> getProjects();

	public void setProject(Project project);

	public Project getProject();

	public void setMonth(Date month);

	public Date getMonth();

	public void endConversation();

	public void startConversation();

	public List<String> getWps();

	public Map<String, StatusReport> getMonthlyReports();

}
