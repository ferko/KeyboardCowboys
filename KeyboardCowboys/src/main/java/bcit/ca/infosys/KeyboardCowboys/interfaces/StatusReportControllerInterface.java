package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.Date;
import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

public interface StatusReportControllerInterface {

	public void startConversation();
	public void endConversation();
	public StatusReport getStatusReport();
	public String saveStatusReport();
	public String submitStatusReport();
	public String updateStatusReport();
	public List<WorkPackage> getWorkPackages();
	public Date getReportDate();
	public void setReportDate(Date reportDate);
	public WorkPackage getWorkPackage();
	public void setWorkPackage(WorkPackage workPackage);
}
