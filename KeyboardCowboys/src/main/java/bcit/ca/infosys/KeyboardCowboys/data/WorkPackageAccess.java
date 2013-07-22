package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;
import bcit.ca.infosys.KeyboardCowboys.util.EmployeeComparer;

/**
 * Provides access by ID and all to work package entity
 * 
 * @author Andrej K
 * 
 */

@Local(WorkPackageAccessInterface.class)
@Stateless
public class WorkPackageAccess implements WorkPackageAccessInterface {
	/**
	 * Entity Manager used to access the database
	 */
	@Inject
	private EntityManager em;
	/**
	 * Logger which is used to print events to the log
	 */
	@Inject
	private Logger log;

	/**
	 * Gets work package by id
	 * 
	 * @param ID
	 * @return
	 */
	public WorkPackage getWorkPackageByID(WorkPackage wPack) {
		TypedQuery<WorkPackage> query = em
				.createQuery(
						"SELECT DISTINCT w FROM WorkPackage w LEFT JOIN FETCH w.wpProject p LEFT JOIN FETCH p.projEmployees WHERE w = :wpID",
						WorkPackage.class);
		query.setParameter("wpID", wPack);
		WorkPackage wp = query.getSingleResult();
		 for(WorkPackageBudget budget : wp.getWpBudgets())
		 {
			 PayLevel pLevel= budget.getPayLevel();
		 }
		@SuppressWarnings("unused")
		Set<Employee> temp = wp.getWpProject().getProjEmployees();
		Collections.sort(wp.getWpProject().getProjEmployeesList(),
				new EmployeeComparer());
		Collections.sort(wp.getWpEmployees(), new EmployeeComparer());
		@SuppressWarnings("unused")
		Employee emp = wp.getWpResEngineer();
		return wp;
	}

	/**
	 * Get all work packages
	 * 
	 * @return
	 */
	public List<WorkPackage> getAllWorkPackages() {
		TypedQuery<WorkPackage> query = em.createQuery(
				"SELECT w FROM WorkPackage w", WorkPackage.class);
		return query.getResultList();
	}

	@Override
	public List<WorkPackage> getWorkPackageByResEngineer(
			Employee currentEmployee) {
		return em
				.createQuery(
						"SELECT DISTINCT wp FROM WorkPackage wp LEFT JOIN FETCH wp.resBudget WHERE wp.wpResEngineer = :employee", WorkPackage.class)
				.setParameter("employee", currentEmployee).getResultList();
	}

}
