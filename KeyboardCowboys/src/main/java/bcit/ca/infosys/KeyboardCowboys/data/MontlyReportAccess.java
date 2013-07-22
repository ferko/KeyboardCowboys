package bcit.ca.infosys.KeyboardCowboys.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.MonthyReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.StatusReportAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.WPEmpEstimate;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;

@Stateless
@Local(MonthyReportAccessInterface.class)
public class MontlyReportAccess implements Serializable, MonthyReportAccessInterface{
	
	@Inject
	private EntityManager em;
	@Inject
	private Logger log;
	@Inject
	private StatusReportAccessInterface statusReportAccess;
	
	@Override
	public Map<String, StatusReport> getMonthyReport(Project proj, Date date)
	{
		Map<String, StatusReport> map = new LinkedHashMap<String, StatusReport>();
		TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage wp JOIN FETCH wp.wpBudgets WHERE wp.wpPackage IS EMPTY AND wp.wpProject = :proj", WorkPackage.class);
		List<WorkPackage> workPackages = query.setParameter("proj", proj).getResultList();
		for(WorkPackage wp: workPackages)
		{
			traverseChildren(wp, date, map);
		}
		return map;
	}
	@Override
	public List<WPEmpEstimate> traverseChildren(WorkPackage wp, Date date, Map<String, StatusReport> map)
	{
		for(WorkPackageBudget tempBud : wp.getResBudget())
		{
			log.info("" + tempBud.getWpbID());
		}
		StatusReport sr = new StatusReport();
		if(wp.getWpPackages().isEmpty())
		{
			sr = statusReportAccess.getStatusReportByWP(wp, date);
		}
		else
		{
			sr.setWorkPackage(wp);
			wp.setResBudget(new ArrayList<WorkPackageBudget>());
			wp.setWpBudgets(new ArrayList<WorkPackageBudget>());
			sr.setEmpEstimate(new ArrayList<WPEmpEstimate>());
			for(WorkPackage child : wp.getWpPackages())
			{

				wp.getResBudget().addAll(child.getResBudget());
				wp.getWpBudgets().addAll(child.getWpBudgets());
				List<WPEmpEstimate> temp = traverseChildren(child, date, map);
				sr.getEmpEstimate().addAll(temp);
			}
		}
		map.put(sr.getWorkPackage().getWpID(), sr);
		return sr.getEmpEstimate();
	}	
}
