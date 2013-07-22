package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetApprovalAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;

/**
 * 
 * @author Andrej K
 * 
 */
@Stateless
@Local(TimeSheetApprovalAccessInterface.class)
public class TimeSheetApprovalAccess implements
		TimeSheetApprovalAccessInterface {

	@Inject
	private Logger log;
	@Inject
	private EntityManager em;

	@Override
	public List<TimeSheetApproval> getTimeSheetApprovalsByEmployee(
			Employee employee) {
		log.info("Getting Approve Time Sheet for" + employee.getEmpID()
				+ " from DB @ ApproveTimeSheetAccess.class");
		TypedQuery<TimeSheetApproval> query = em
				.createQuery(
						"SELECT DISTINCT t FROM TimeSheetApproval t JOIN FETCH t.tsaApprover JOIN FETCH t.timeSheet ts JOIN FETCH ts.tsEmp JOIN FETCH ts.tsTimeRows tr JOIN FETCH tr.trProject JOIN FETCH tr.trWorkPackage WHERE t.tsaApprover = :emp AND ts.status = 'Submitted'",
						TimeSheetApproval.class);
		return query.setParameter("emp", employee).getResultList();
	}

}
