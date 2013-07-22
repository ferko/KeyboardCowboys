package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.WPEmpEstimate;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

public interface MonthyReportAccessInterface {

	public Map<String, StatusReport> getMonthyReport(Project proj, Date date);
	public List<WPEmpEstimate> traverseChildren(WorkPackage wp, Date date,
			Map<String, StatusReport> map);

}
