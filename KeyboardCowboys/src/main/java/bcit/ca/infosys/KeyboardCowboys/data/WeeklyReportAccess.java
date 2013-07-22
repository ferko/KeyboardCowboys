package bcit.ca.infosys.KeyboardCowboys.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bcit.ca.infosys.KeyboardCowboys.interfaces.WeeklyReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReport;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReportRow;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

@Local(WeeklyReportAccessInterface.class)
@Stateless
public class WeeklyReportAccess implements Serializable,
		WeeklyReportAccessInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	@Override
	public WeeklyReport getWeeklyReportByProject(Project project, Date date) {
		WeeklyReport wr;
		wr = new WeeklyReport();
		wr.setWrDate(date);
		wr.setWrProject(project);
		wr.setEmpMap(new LinkedHashMap<Employee, List<WeeklyReportRow>>());
		wr.setWpMap(new LinkedHashMap<WorkPackage, List<WeeklyReportRow>>());
		List<WorkPackage> workPacks = em
				.createQuery(
						"SELECT DISTINCT w FROM WorkPackage w WHERE w.wpProject = :proj AND w.wpPackage IS NULL",
						WorkPackage.class).setParameter("proj", project)
				.getResultList();
		for (WorkPackage wp : workPacks) {
			if (wp.getWpPackage() == null) {
				log.info("WP from project: " + wp.getWpName());
				calculateWeeklyReport(wp, wr);
			}
		}

		for (List<WeeklyReportRow> wrrList : wr.getWpMap().values()) {
			for (WeeklyReportRow weekRow : wrrList) {
				if (weekRow.getWrrEmployee() != null) {
					List<WeeklyReportRow> value2 = wr.getEmpMap().get(
							weekRow.getWrrEmployee());
					log.info(weekRow.getWrrWorkPackage().getWpName() + " "
							+ weekRow.getWrrEmployee().getEmpFName());
					if (value2 == null) {
						value2 = new ArrayList<WeeklyReportRow>();
						value2.add(weekRow);
						wr.getEmpMap().put(weekRow.getWrrEmployee(), value2);
					}
					else
					{
						value2.add(weekRow);
					}
				}
			}
		}
		return wr;
	}

	public void calculateWeeklyReport(WorkPackage wp, WeeklyReport wr) {
		log.info("Checking children for: " + wp.getWpName());
		if (wp.getWpPackages().isEmpty()) {
			calculateRows(wp, wr);
		} else {
			List<WeeklyReportRow> wrrs = new ArrayList<WeeklyReportRow>();
			for (WorkPackage workPack : wp.getWpPackages()) {
				log.info("Child: " + workPack.getWpName() + " for: "
						+ wp.getWpName());
				calculateWeeklyReport(workPack, wr);
				WeeklyReportRow wrr = new WeeklyReportRow();
				wrr.setWrrWorkPackage(workPack);
				for (WeeklyReportRow tempWrr : wr.getWpMap().get(workPack)) {
					wrr.setCurrentWeek(wrr.getCurrentWeek()
							+ tempWrr.getCurrentWeek());
					wrr.setCurrentMonth(wrr.getCurrentMonth()
							+ tempWrr.getCurrentMonth());
					wrr.setWrrTotal(wrr.getWrrTotal() + tempWrr.getWrrTotal());
				}
				wrrs.add(wrr);
			}
			wr.getWpMap().put(wp, wrrs);
		}
	}

	public void calculateRows(WorkPackage wp, WeeklyReport wr) {

		Map<Employee, WeeklyReportRow> map = new HashMap<Employee, WeeklyReportRow>();
		List<TimeRow> trs = em
				.createQuery(
						"SELECT DISTINCT tr FROM TimeRow tr WHERE tr.trWorkPackage = :workPackage AND tr.trTimesheet.tsWeekEnding <= :date"
								+ " AND tr.trTimesheet.status = 'Approved' ORDER BY tr.trTimesheet.tsWeekEnding",
						TimeRow.class).setParameter("workPackage", wp)
				.setParameter("date", wr.getWrDate()).getResultList();
		log.info("Calculating rows for: " + wp.getWpName());
		log.info("Rows Found: " + trs.size());
		for (TimeRow tr : trs) {
			log.info(tr.getTrWorkPackage().getWpName());
			WeeklyReportRow value1 = map.get(tr.getTrTimesheet().getTsEmp());
			if (value1 == null) {
				value1 = new WeeklyReportRow();
			}

			if (tr.getTrTimesheet().getTsWeekEnding().compareTo(wr.getWrDate()) == 0) {
				log.info("assigning current week");
				value1.setCurrentWeek(value1.getCurrentWeek()
						+ (tr.getTotal() / 8));
			}
			if (tr.getTrTimesheet().getTsWeekEnding().getMonth() == wr
					.getWrDate().getMonth()) {
				value1.setCurrentMonth(value1.getCurrentMonth()
						+ (tr.getTotal() / 8));
			}
			value1.setWrrTotal(value1.getWrrTotal() + (tr.getTotal() / 8));
			value1.setWrrEmployee(tr.getTrTimesheet().getTsEmp());
			value1.setWrrWorkPackage(wp);
			map.put(tr.getTrTimesheet().getTsEmp(), value1);
		}
		log.info("" + map.size());
		/*
		 * for (WeeklyReportRow weekRow : map.values()) { if
		 * (weekRow.getWrrEmployee() != null) { List<WeeklyReportRow> value2 =
		 * wr.getEmpMap().get( weekRow.getWrrEmployee()); if (value2 == null) {
		 * value2 = new ArrayList<WeeklyReportRow>();
		 * wr.getEmpMap().put(weekRow.getWrrEmployee(), value2); }
		 * value2.add(weekRow); log.info(weekRow.getWrrWorkPackage().getWpName()
		 * + " " + weekRow.getWrrEmployee().getEmpFName()); } }
		 */
		wr.getWpMap().put(wp, new ArrayList<WeeklyReportRow>(map.values()));
	}
}