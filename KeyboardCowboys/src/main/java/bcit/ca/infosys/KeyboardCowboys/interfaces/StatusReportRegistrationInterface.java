package bcit.ca.infosys.KeyboardCowboys.interfaces;

import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;

public interface StatusReportRegistrationInterface {
	public void saveStatusReport(StatusReport statusReport);
	public void mergeStatusReport(StatusReport statusReport);
}
