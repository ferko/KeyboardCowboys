package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReport;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReportRow;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;



public interface WeeklyReportControllerInterface {

    public WeeklyReport getWeeklyReport();
    public void startConversation();
	public void endConversation();
	public List<Project> getProjects();
	public Project getProject();
	public void setProject(Project project);
	public Date getDate();
	public void setDate(Date date);
	public List<Employee> getEmps();
	public List<WorkPackage> getWps();
	public Double getTotalCurrentWeek(List<WeeklyReportRow> list);
	public Double getTotalCurrentMonth(List<WeeklyReportRow> list);
	public Double getTotal(List<WeeklyReportRow> list);
}
