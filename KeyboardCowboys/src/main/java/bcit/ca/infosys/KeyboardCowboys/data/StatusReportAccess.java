package bcit.ca.infosys.KeyboardCowboys.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.StatusReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeRowAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.WPEmpEstimate;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;

@Local(StatusReportAccessInterface.class)
@Stateless
public class StatusReportAccess implements Serializable,
		StatusReportAccessInterface {
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;
	@Inject
	private TimeRowAccessInterface timeRowAccess;

	public StatusReport getStatusReportByWP(WorkPackage workPackage, Date date) {
		StatusReport sr;
		TypedQuery<StatusReport> query = em
				.createQuery(
						"SELECT DISTINCT sr FROM StatusReport sr JOIN FETCH sr.empEstimate JOIN FETCH sr.workPackage w JOIN FETCH w.wpProject p JOIN FETCH p.projManager WHERE sr.workPackage = :WP AND sr.srDate = :date",
						StatusReport.class);
		query.setParameter("WP", workPackage);
		query.setParameter("date", date);
		List<StatusReport> reports = query.getResultList();
		if (!reports.isEmpty()) {
			for(WorkPackageBudget tempBug : reports.get(0).getWorkPackage().getWpBudgets())
			{
				log.info("" + tempBug.getWpbID());
			}
			return reports.get(0);
		} else {
			sr = new StatusReport();
			workPackage = em.find(WorkPackage.class, workPackage.getDbID());
			sr.setWorkPackage(workPackage);
			sr.setSrDate(date);
			sr.setProjAssistant(workPackage.getWpProject().getProjAssistant());
			log.info(workPackage.getWpProject().getProjManager().getEmpFName());
			sr.setProjManager(workPackage.getWpProject().getProjManager());
			sr.setWpAssistant(workPackage.getWpResAssistant());
			log.info(workPackage.getWpResEngineer().getEmpFName());
			sr.setWpResEngineer(workPackage.getWpResEngineer());
			List<WPEmpEstimate> estimates = new ArrayList<WPEmpEstimate>();
			for (Employee employee : workPackage.getWpEmployees()) {
				WPEmpEstimate estimate = new WPEmpEstimate();
				estimate.setEmployee(employee);
				log.info(employee.getEmpFName());
				estimate.setPayWage(employee.getEmpPayInfo().getEpiPayLevel()
						.getPlRate());
				List<TimeRow> timeRows = timeRowAccess
						.getTimeRowByDateEmployeeWorkPackage(employee, date,
								workPackage);
				estimate.setTimeSheetSubmission(null);
				if (!timeRows.isEmpty()) {
					estimate.setTimeSheetSubmission(0.0);
					for (TimeRow tr : timeRows) {
						estimate.setTimeSheetSubmission(estimate
								.getTimeSheetSubmission() + (tr.getTotal() / 8));
					}
					if (timeRows.get(0).getTrTimesheet().getTsWeekEnding()
							.compareTo(date) != 0) {
						estimate.setTimeSheetSubmission(estimate
								.getTimeSheetSubmission() * -1);
					}
				}
				estimates.add(estimate);
			}
			sr.setEmpEstimate(estimates);
		}

		for (WorkPackageBudget budget : sr.getWorkPackage().getWpBudgets()) {
			PayLevel plevel = budget.getPayLevel();
			log.info(plevel.getPlLevel());
		}

		return sr;
	}

}
