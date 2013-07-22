package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;

import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReport;


public interface WeeklyReportAccessInterface {
	public WeeklyReport getWeeklyReportByProject(Project project, Date date);
}
