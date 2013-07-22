package bcit.ca.infosys.KeyboardCowboys.interfaces;



import java.util.Date;

import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

public interface StatusReportAccessInterface {
	public StatusReport getStatusReportByWP(WorkPackage workPackage, Date date);
}
